package com.sunlight.blc.constant;

/**
 * 
 * @author gaoguang
 * @description 设备类型
 * 2016-12-29下午2:32:56
 */
public enum FileTypeEnum {
	
	/*可播视频，音频，图片，其他*/

    video("video","可播视频"),
	audio("audio","音频"),
    image("image","图片"),
	file("file","其他");
	private String key;
	private String desc;
	
	private FileTypeEnum(){
		
	}
	
	private FileTypeEnum(String key,String desc){
		this.key = key;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
