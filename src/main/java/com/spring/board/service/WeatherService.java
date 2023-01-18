package com.spring.board.service;

import java.util.List;

import com.spring.board.dto.WeatherVO;

public interface WeatherService {
	
	public List<WeatherVO> selectWeatherList(WeatherVO weatherVO);
	
	public WeatherVO selectOneWeather (WeatherVO weatherVO);

	public boolean insertWeather (WeatherVO weatherVO);
	
	public boolean updateWeather (WeatherVO weatherVO);
	
	public boolean deleteWeather (WeatherVO weatherVO);

}
