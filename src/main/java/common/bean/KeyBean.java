package main.java.common.bean;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 암호화 키 객체 클래스
 * 
 * @author RWB
 *
 * @since 2020.10.02 Thu 02:01
 */
public class KeyBean
{
	// 공개키
	private PublicKey publicKey;
	
	// 개인키
	private PrivateKey privateKey;
	
	/**
	 * 공개키 반환 함수
	 * 
	 * @return [PublicKey] publicKey: 공개키
	 */
	public PublicKey getPublicKey()
	{
		return publicKey;
	}
	
	/**
	 * 공개키 할당 함수
	 * 
	 * @param [PublicKey] publicKey: 공개키
	 * 
	 * @return [void]: PublicKey 객체 할당
	 */
	public void setPublicKey(PublicKey publicKey)
	{
		this.publicKey = publicKey;
	}
	
	/**
	 * 개인키 반환 함수
	 * 
	 * @return [PrivateKey] privateKey: 개인키
	 */
	public PrivateKey getPrivateKey()
	{
		return privateKey;
	}
	
	/**
	 * 개인키 할당 함수
	 * 
	 * @param [PrivateKey] privateKey: 개인키
	 * 
	 * @return [void]: PrivateKey 객체 할당
	 */
	public void setPrivateKey(PrivateKey privateKey)
	{
		this.privateKey = privateKey;
	}
}