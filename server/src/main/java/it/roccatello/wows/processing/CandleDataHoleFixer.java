package it.roccatello.wows.processing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Maps;

import it.roccatello.wows.model.dto.DtoCandle;
import it.roccatello.wows.service.IntervalService.IntervalConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CandleDataHoleFixer {
  private final long step;

  public CandleDataHoleFixer(IntervalConst interval) {
    this.step = interval.epochStep();
  }

  /**
   * 
   * @param input sorted list of DtoCandles (same ticker and interval type)
   * @return
   */
  public List<DtoCandle> process(List<DtoCandle> input) {
    // find holes and duplicated
    var holeAfter = Maps.<Integer, Long>newHashMap();
    for (int i = 0; i < input.size() - 1; ++i) {
      var s = input.get(i).getOccurred();
      var e = input.get(i+1).getOccurred();
      if (e - s > this.step) {
        holeAfter.put(i, (long)Math.ceil((e - s) / this.step) - 1);
      } else if (e == s) {
        log.warn("Duplicated data found for input");
      }
    }
    if (!holeAfter.isEmpty()) {
      log.warn("Found {} hole(s) with max span {} intervals.", holeAfter.size(), Collections.max(holeAfter.values()));
    }

    // close holes
    var output = new ArrayList<DtoCandle>(input.size());
    for (int i = 0; i < input.size(); ++i) {
      var c1 = input.get(i);
      output.add(c1);
      if (holeAfter.containsKey(i)) {
        var lastValue = c1.getClose();
        var holeSize = holeAfter.get(i);
        var c2 = input.get(i + 1);
        var valueStep = c2.getOpen().subtract(c1.getClose()).divide(BigDecimal.valueOf(holeSize), 10, RoundingMode.HALF_UP);
        
        for (int count = 0; count < holeSize; ++count) {
          var dto = new DtoCandle();
          dto.setGenerated(true);
          dto.setVolume(BigDecimal.valueOf(0));
          dto.setOccurred(c1.getOccurred() + (count + 1) * this.step);
          dto.setOpen(lastValue);
          lastValue = lastValue.add(valueStep);
          dto.setClose(lastValue);

          dto.setMax(dto.getOpen().max(dto.getClose()));
          dto.setMin(dto.getOpen().min(dto.getClose()));
          output.add(dto);
        }
      }
    }

    return output;
  }

}
