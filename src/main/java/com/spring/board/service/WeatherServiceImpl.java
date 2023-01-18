package com.spring.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.spring.board.dao.WeatherDao;
import com.spring.board.dto.WeatherVO;


@Service
public class WeatherServiceImpl implements WeatherService{

	private String whoAmi;

	@Autowired
	private ExceptionFactory exceptionFactory;
	
	@Autowired
	private WeatherDao weatherDao;
	
	@Override
	public List<WeatherVO> selectWeatherList(WeatherVO weatherVO){
		whoAmi = Thread.currentThread().getStackTrace()[1].getMethodName();
		List<WeatherVO> weatherList = new ArrayList<WeatherVO>();
		try{
			
			weatherList = weatherDao.selectWeatherList(weatherVO);
			
		} catch (Exception e) {
	
			e.printStackTrace();
	
		}
		return weatherList;
	}
	
	@Override
	public WeatherVO selectOneWeather(WeatherVO weatherVO){
		whoAmi = Thread.currentThread().getStackTrace()[1].getMethodName();
		try{
			
			weatherVO = weatherDao.selectOneWeather(weatherVO);
			
		} catch (Exception e) {
	
			e.printStackTrace();
		}
		return weatherVO;
	}
	
	@Override
	public boolean insertWeather(WeatherVO weatherVO) {
		return weatherDao.insertWeather(weatherVO) > 0;
	}
	
	@Override
	public boolean updateWeather(WeatherVO weatherVO) {
		return weatherDao.updateWeather(weatherVO) > 0;
	}
	
	@Override
	public boolean deleteWeather(WeatherVO weatherVO) {
		return weatherDao.deleteWeather(weatherVO) > 0;
	}
	
	
}
