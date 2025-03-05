package it.roccatello.wows.processing;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.ta4j.core.indicators.candles.BullishEngulfingIndicator;
import org.ta4j.core.indicators.volume.OnBalanceVolumeIndicator;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.roccatello.wows.model.dto.DtoCandle;
import it.roccatello.wows.service.IntervalService.IntervalConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class CandleProcessingTests {

	@Autowired
	protected ObjectMapper mapper;

	@Autowired
	private ResourceLoader resourceLoader = null;

	private List<DtoCandle> candles;

	@BeforeEach
	private void setup() throws StreamReadException, DatabindException, IOException {
		var interpolator = new CandleDataHoleInterpolator(IntervalConst.I_1H);
		this.candles = interpolator.process(this.loadData("classpath:candles-1h.json"));
	}

	private List<DtoCandle> loadData(String file) {
		try {
			var url = resourceLoader.getResource(file).getFile();
			var rawData = this.mapper.readValue(url, new TypeReference<List<Map<String, BigDecimal>>>() {
			});

			return rawData.stream().map(raw -> {
				var dto = new DtoCandle();
				dto.setClose(raw.get("close"));
				dto.setMax(raw.get("max"));
				dto.setMin(raw.get("min"));
				dto.setOpen(raw.get("open"));
				dto.setOccurred(raw.get("occurred").toBigInteger().longValue());
				dto.setVolume(raw.get("volume"));
				dto.setId(raw.get("id").toBigInteger().longValue());
				return dto;
			}).sorted((c1, c2) -> c1.getOccurred().compareTo(c2.getOccurred())).toList();
		} catch (Exception e) {
			log.error("{}", e);
		}
		return null;
	}

	@Test
	void holesTest() {
		var brokenCandles = this.loadData("classpath:candles-1h-holes.json");
		var interpolator = new CandleDataHoleInterpolator(IntervalConst.I_1H);
		var okCandles = interpolator.process(brokenCandles);
		log.debug("{} --> {}", brokenCandles.size(), okCandles.size());
	}

	@Test
	void obvTest() {
		var obv = new OnBalanceVolumeIndicator(BarSeriesConverter.convert(this.candles));
		log.info("{}", obv);	
	}

	@Test
	void bullishEng() {
		var bueng = new BullishEngulfingIndicator(BarSeriesConverter.convert(this.candles));
		log.info("{}", bueng);
	}

}
