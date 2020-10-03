package main.java.common.exception;

/**
 * 아이디 불일치 예외
 * 
 * @author RWB
 *
 * @since 2020.10.02 Thu 17:28
 */
public class NotMatchedPasswordException extends Exception
{
	private static final long serialVersionUID = -507256679382754207L;
	
	/**
	 * NotMatchedIdException 생성자 함수
	 * 
	 * @return [void]: NotMatchedIdException 초기화
	 */
	public NotMatchedPasswordException()
	{
		super();
	}
	
	/**
	 * NotMatchedIdException 생성자 함수
	 * 
	 * @param [String] reason: 예외 사유
	 * 
	 * @return [void]: NotMatchedIdException 초기화
	 */
	public NotMatchedPasswordException(String reason)
	{
		super(reason);
	}
}