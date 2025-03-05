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

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.roccatello.wows.model.dto.DtoCandle;

@SpringBootTest
@ActiveProfiles("test")
class CandleProcessingTests {

	@Autowired
	protected ObjectMapper mapper;

	@Autowired
	private ResourceLoader resourceLoader = null;

	private List<Map<String, BigDecimal>> rawData;
	private List<DtoCandle> candles;

	@BeforeEach
	private void setup() throws StreamReadException, DatabindException, IOException {
		var url = resourceLoader.getResource("classpath:candles-1h.json").getFile();
		this.rawData = this.mapper.readValue(url, new TypeReference<List<Map<String, BigDecimal>>>() {
		});

		this.candles = this.rawData.stream().map(raw -> {
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
	}

	@Test
	void holesTest() {
	}

	@Test
	void obvTest() {
	}

}
