package pl.edu.agh.mczernek.mandown.utils;

public class FilenameUtils {
	
	public static final String EXTENSION = "mandown";

	public static String getFilenameWithoutExtension(String filename){
		if(null == filename){
			return null;
		}else{
			return filename.substring(0, hetIndexOfFirstDotOrLastLetter(filename));
		}
	}

	private static int hetIndexOfFirstDotOrLastLetter(String filename) {
		int result = filename.indexOf('.');
		if(-1 != result){
			return result;
		}else{
			return filename.length();
		}
	}

	public static String getFilenameWithExtension(String filename){
		if(isEmpty(filename)){
			return null;
		}else{
			return getFilenameWithoutExtension(filename) + "." + EXTENSION;
		}
	}
	
	public static boolean isEmpty(String filename){
		return (null == filename || filename.equals(""));
	}

	public static String getEndNumber(String filename) {
		if(isEmpty(filename)) return null;
		String toProcess = getFilenameWithoutExtension(filename);
		int indexOfFirstNumber = getIndexOfFirstNumber(toProcess);
		if(indexOfFirstNumber < toProcess.length())
			return toProcess.substring(indexOfFirstNumber);
		else
			return null;
		
	}

	private static int getIndexOfFirstNumber(String toProcess) {
		int indexOfFirstNumber = toProcess.length()-1;
		for(; indexOfFirstNumber>=0; --indexOfFirstNumber){
			if(!Character.isDigit(toProcess.charAt(indexOfFirstNumber)))
				break;
		}
		return indexOfFirstNumber+1;
	}

	
	/*public static String getIncrementedNumber(String filename) {
		if(isEmpty(filename)) return "0";
		String toIncrement = getEndNumber(filename);
		return incrementNumber(toIncrement);
	}*/
	
	public static String getStringWithoutEndNumber(String filename){
		if(isEmpty(filename)) return null;
		int indexOfFirstNumber = getIndexOfFirstNumber(filename);
		return filename.substring(0, indexOfFirstNumber);
	}

	public static String getIncrementedTextNumber(String toIncrement) {
		if(isEmpty(toIncrement)) return "0";
		StringBuilder sb = new StringBuilder(toIncrement);
		boolean endOfIncrementation = false;
		int i = toIncrement.length()-1;
		while(!endOfIncrementation && i >= 0){
			sb.setCharAt(i, incrementedChar(sb.charAt(i)));
			if(sb.charAt(i) != '0'){
				endOfIncrementation = true;
				break;
			}
			--i;
		}
		if(!endOfIncrementation){
			return "1" + sb.toString();
		}else{
			return sb.toString();
		}
	}

	private static char incrementedChar(char charAt) {
		switch(charAt){
			case '0': return '1';
			case '1': return '2';
			case '2': return '3';
			case '3': return '4';
			case '4': return '5';
			case '5': return '6';
			case '6': return '7';
			case '7': return '8';
			case '8': return '9';
			case '9': return '0';
			default: return '0';
		}
	}


	public static String getIncrementedFilename(String filename) {
		String temporary = getFilenameWithoutExtension(filename);
		String name = getStringWithoutEndNumber(temporary);
		String number = getIncrementedTextNumber(getEndNumber(temporary));
		return parseFilename(name, number);
	}
	/*private static String incrementNumber(String toIncrement) {
		
	}*/

	private static String parseFilename(String name, String number) {
		StringBuilder sb = new StringBuilder();
		if(name!=null){
			sb.append(name);
		}
		if(number!=null){
			sb.append(number);
		}else{
			sb.append("");
		}
		return sb.toString();
	}
	
}
