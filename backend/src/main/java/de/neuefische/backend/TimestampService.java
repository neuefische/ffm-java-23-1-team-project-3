package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimestampService {

	private final TimestampRepository timestampRepository;

	public void setTimestampToNow() {
		timestampRepository.save(Timestamp.now());
	}

	public Timestamp getCurrentTimestamp() {
		List<Timestamp> timestamps = timestampRepository.findAll();
		return timestamps.isEmpty() ? null : timestamps.get(0);
	}

}
