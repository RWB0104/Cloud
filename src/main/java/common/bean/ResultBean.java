package main.java.common.bean;

/**
 * 결과 Bean 클래스
 * 
 * @author RWB
 *
 * @param <T>: 제네릭 클래스
 */
public class ResultBean<T>
{
	// 정상동작 여부
	private boolean flag;
	
	// 제목
	private String title;
	
	// 내용
	private String description;
	
	// 결과 객체
	private T result;
	
	/**
	 * 정상동작 여부 반환 함수
	 * 
	 * @return [boolean] flag: 정상동작 여부
	 */
	public boolean isFlag()
	{
		return flag;
	}
	
	/**
	 * 정상동작 여부 할당 함수
	 * 
	 * @param [boolean] flag: 정상동작 여부
	 * 
	 * @return [void]: 정상동작 여부 할당
	 */
	public void setFlag(boolean flag)
	{
		this.flag = flag;
	}
	
	/**
	 * 제목 반환 함수
	 * 
	 * @return [String] title: 제목
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * 제목 할당 함수
	 * 
	 * @param [String] title: 제목
	 * 
	 * @return [void]: 제목 할당
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	/**
	 * 내용 반환 함수
	 * 
	 * @return [String] description: 내용
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * 내용 할당 함수
	 * 
	 * @param [String] description: 내용
	 * 
	 * @return [void]: 내용 할당
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	/**
	 * 결과 객체 반환 함수
	 * 
	 * @return [T] result: 결과 객체
	 */
	public T getResult()
	{
		return result;
	}
	
	/**
	 * 결과 객체 할당 함수
	 * 
	 * @param [T] result: 결과 객체
	 * 
	 * @return [void]: 결과 객체 할당
	 */
	public void setResult(T result)
	{
		this.result = result;
	}
}