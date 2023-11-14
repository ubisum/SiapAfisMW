package it.mgg.siapafismw.service;

import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dto.ColloquioDTO;
import it.mgg.siapafismw.dto.InsertUpdateDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;

@Service("colloquioServiceImpl")
public class ColloquioServiceImpl implements ColloquioService {

	@Override
	public ColloquioDTO insertUpdateColloquio(InsertUpdateDTO insertUpdate) throws SiapAfisMWException {
		return null;
	}

}
