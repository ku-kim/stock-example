package dev.kukim.stock.service;

import dev.kukim.stock.domain.Stock;
import dev.kukim.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

	private final StockRepository stockRepository;

	public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

//	@Transactional
	/*
	synchronized의 문제 - synchronized의는 프로세스 단위로 묶여있기 때문에 2대 이상의 서버 사용 시 race condition이 여전히 발생함
	 */
	public synchronized void decrease(Long id, Long quantity) {
		Stock stock = stockRepository.findById(id).orElseThrow();

		stock.decrease(quantity);

		stockRepository.saveAndFlush(stock);
	}
}
