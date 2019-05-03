package application;

import java.util.Random;

public class colorMap {
	private String colorMaps[];
	
	public colorMap(int size) {
		colorMaps = new String[size];
		
		for(int i = 0; i<size; i++) {
			colorMaps[i]="#";
			Random random = new Random();
			int a = random.nextInt(16777215);
			String s = Integer.toHexString(a);
			for(int j=6; j>s.length(); j--) {
				colorMaps[i]+="0";
			}
			colorMaps[i]+=Integer.toHexString(a);
		}
	}
	
	public String getColorOfI(int pos) {
		if(pos > colorMaps.length) return "#000000";
		return colorMaps[pos];
	}
	
	public void addColorMap() {
		String tmp[] = colorMaps.clone();
		colorMaps = new String[colorMaps.length+1];
		for(int i = 0; i<colorMaps.length-1; i++) {
			colorMaps[i]=tmp[i];
		}
		colorMaps[colorMaps.length-1]="#";
		Random random = new Random();
		int a = random.nextInt(16777215);
		String s = Integer.toHexString(a);
		for(int j=6; j>s.length(); j--) {
			colorMaps[colorMaps.length-1]+="0";
		}
		colorMaps[colorMaps.length-1]+=Integer.toHexString(a);
	}
	
	public void removeColorMap() {
		String tmp[] = colorMaps.clone();
		colorMaps = new String[colorMaps.length-1];
		for(int i = 0; i<colorMaps.length; i++) {
			colorMaps[i]=tmp[i];
		}
	}
	
	public void resetColorPosI(int pos) {
		if(pos > colorMaps.length) return ;
		colorMaps[pos]="#";
		Random random = new Random();
		int a = random.nextInt(16777215);
		String s = Integer.toHexString(a);
		for(int j=6; j>s.length(); j--) {
			colorMaps[pos]+="0";
		}
		colorMaps[pos]+=Integer.toHexString(a);
		
	}
	
	public int getSize() {
		return colorMaps.length;
	}
}
