package main.java.common.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import main.java.common.bean.KeyBean;

/**
 * RSA 암복호화 클래스
 * 
 * @author RWB
 * 
 * @since 2020.10.01 Thu 03:59
 */
public class Cipher
{
	private static Cipher instance = new Cipher();
	
	private KeyPair pair;
	
	/**
	 * Cipher 생성자 함수
	 * 
	 * @return [void]: 인스턴스 생성 방지
	 */
	private Cipher()
	{
		genKeyPair();
	}
	
	/**
	 * Cipher 인스턴스 반환 함수
	 * 
	 * @return [Cipher] instance: Cipher 싱글톤 인스턴스
	 */
	public static Cipher getInstance()
	{
		return instance;
	}
	
	/**
	 * KeyBean 반환 함수
	 * 
	 * @return [KeyBean] bean: 키쌍이 담긴 KeyBean 객체
	 */
	public KeyBean getKey()
	{
		KeyBean bean;
		
		// 키 확인 시도
		try
		{
			String root = Util.getINFPath();
			
			String privatePath = root + File.separator + "private.pem";
			String publicPath = root + File.separator + "public.pem";
			
			File privateFile = new File(privatePath);
			File publicFile = new File(publicPath);
			
			// 개인키 파일이 존재하지 않을 경우
			if (!privateFile.exists())
			{
				savePrivateKey();
			}
			
			// 공개키 파일이 존재하지 않을 경우
			if (!publicFile.exists())
			{
				savePublicKey();
			}
			
			DataInputStream privateStream = new DataInputStream(new FileInputStream(privateFile));
			DataInputStream publicStream = new DataInputStream(new FileInputStream(publicFile));
			
			byte[] privateBytes = privateStream.readAllBytes();
			byte[] publicBytes = publicStream.readAllBytes();
			
			privateStream.close();
			publicStream.close();
			
			String privateStr = new String(privateBytes).replace("-----BEGIN RSA PRIVATE KEY-----", "").replace("-----END RSA PRIVATE KEY-----", "").replaceAll(System.lineSeparator(), "");
			String publicStr = new String(publicBytes).replace("-----BEGIN RSA PUBLIC KEY-----", "").replace("-----END RSA PUBLIC KEY-----", "").replaceAll(System.lineSeparator(), "");
			
			privateBytes = Base64.getDecoder().decode(privateStr);
			publicBytes = Base64.getDecoder().decode(publicStr);
			
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateBytes);
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicBytes);
			
			KeyFactory factory = KeyFactory.getInstance("RSA");
			
			bean = new KeyBean();
			bean.setPrivateKey(factory.generatePrivate(privateKeySpec));
			bean.setPublicKey(factory.generatePublic(publicKeySpec));
		}
		
		// 파일 누락
		catch (FileNotFoundException e)
		{
			Util.sysln(this.getClass(), "키 파일(.pem)이 존재하지 않음");
			
			e.printStackTrace();
			
			bean = null;
		}
		
		// 데이터 입출력 오류
		catch (IOException e)
		{
			Util.sysln(this.getClass(), "데이터 입출력 오류");
			
			e.printStackTrace();
			
			bean = null;
		}
		
		// 유효하지 않은 알고리즘
		catch (NoSuchAlgorithmException e)
		{
			Util.sysln(this.getClass(), "유효하지 않은 알고리즘");
			
			e.printStackTrace();
			
			bean = null;
		}
		
		// 유효하지 않은 KeySpec
		catch (InvalidKeySpecException e)
		{
			Util.sysln(this.getClass(), "유효하지 않은 KeySpec");
			
			e.printStackTrace();
			
			bean = null;
		}
		
		// 오류
		catch (Exception e)
		{
			Util.sysln(this.getClass(), e.getClass().getSimpleName());
			
			e.printStackTrace();
			
			bean = null;
		}
		
		return bean;
	}
	
	/**
	 * 개인키 PEM 저장 함수
	 * 
	 * @return [void]: 지정된 경로에 개인키 저장
	 */
	private void savePrivateKey()
	{
		String root = Util.getINFPath();
		
		StringBuilder builder = new StringBuilder();
		builder.append(root);
		builder.append(File.separator);
		builder.append("private.pem");
		
		root = builder.toString();
		
		File file = new File(root);
		
		// 키 파일이 없을 경우
		if (!file.exists())
		{
			// Pem 생성 시도
			try
			{
				PrivateKey key = pair.getPrivate();
				
				PemObject object = new PemObject("RSA PRIVATE KEY", key.getEncoded());
				
				PemWriter writer = new PemWriter(new OutputStreamWriter(new FileOutputStream(root)));
				writer.writeObject(object);
				writer.close();
			}
			
			// 파일 누락
			catch (FileNotFoundException e)
			{
				Util.sysln(this.getClass(), "키 파일(.pem)이 존재하지 않음");
				
				e.printStackTrace();
			}
			
			// 데이터 입출력 오류
			catch (IOException e)
			{
				Util.sysln(this.getClass(), "데이터 입출력 오류");
				
				e.printStackTrace();
			}
			
			// 오류
			catch (Exception e)
			{
				Util.sysln(this.getClass(), e.getClass().getSimpleName());
				
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 공개키 PEM 저장 함수
	 * 
	 * @return [void]: 지정된 경로에 공개키 저장
	 */
	private void savePublicKey()
	{
		String root = Util.getINFPath();
		
		StringBuilder builder = new StringBuilder();
		builder.append(root);
		builder.append(File.separator);
		builder.append("public.pem");
		
		root = builder.toString();
		
		File file = new File(root);
		
		if (!file.exists())
		{
			// Pem 생성 시도
			try
			{
				PublicKey key = pair.getPublic();
				
				PemObject object = new PemObject("RSA PUBLIC KEY", key.getEncoded());
				
				PemWriter writer = new PemWriter(new OutputStreamWriter(new FileOutputStream(root)));
				writer.writeObject(object);
				writer.close();
			}
			
			// 파일 누락
			catch (FileNotFoundException e)
			{
				Util.sysln(this.getClass(), "키 파일(.pem)이 존재하지 않음");
				
				e.printStackTrace();
			}
			
			// 데이터 입출력 오류
			catch (IOException e)
			{
				Util.sysln(this.getClass(), "데이터 입출력 오류");
				
				e.printStackTrace();
			}
			
			// 오류
			catch (Exception e)
			{
				Util.sysln(this.getClass(), e.getClass().getSimpleName());
				
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 키쌍 생성 함수
	 * 
	 * @return [void]: KeyPair 객체 할당
	 */
	private void genKeyPair()
	{
		// 키쌍 생성 시도
		try
		{
			SecureRandom random = new SecureRandom();
			
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(4096, random);
			
			pair = generator.genKeyPair();
		}
		
		// 유효하지 않은 알고리즘
		catch (NoSuchAlgorithmException e)
		{
			Util.sysln(this.getClass(), "유효하지 않은 알고리즘");
			
			e.printStackTrace();
		}
		
		// 오류
		catch (Exception e)
		{
			Util.sysln(this.getClass(), e.getClass().getSimpleName());
			
			e.printStackTrace();
		}
	}
}