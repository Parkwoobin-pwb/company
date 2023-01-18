package com.spring.board.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.spring.board.dto.WeatherVO;

@Repository
public class WeatherDao {
	/**
	 * 예외 처리 및 트래킹을 위한 멤버 변수
	 */
	private String whoAmi;

	/**
	 * 공통 예외 처리 Autowired
	 */
	@Autowired
	private ExceptionFactory exceptionFactory;
	
	/**
	 * SqlSessionTemplate 클래스  autowired
	 */
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionTemplate;
	
	public List<WeatherVO> selectWeatherList(WeatherVO weatherVO){
		whoAmi = Thread.currentThread().getStackTrace()[1].getMethodName();
		List<WeatherVO> weatherList = new ArrayList<WeatherVO>();
		try {
			
			weatherList = sqlSessionTemplate.selectList("weather.selectWeatherList", weatherVO);
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		return weatherList;
	}
	
	public WeatherVO selectOneWeather (WeatherVO weatherVO) {
		whoAmi = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			
			weatherVO = sqlSessionTemplate.selectOne("weather.selectOneWeather", weatherVO);
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		return weatherVO;
	}
	
	public int insertWeather (WeatherVO weatherVO) {
		
		return sqlSessionTemplate.insert("weather.insertWeather", weatherVO);
	}
	
	public int updateWeather (WeatherVO weatherVO) {
		
		return sqlSessionTemplate.update("weather.updateWeather", weatherVO);
	}
	
	public int deleteWeather (WeatherVO weatherVO) {
		
		return sqlSessionTemplate.update("weather.deleteWeather", weatherVO);
	}
	
	

}
