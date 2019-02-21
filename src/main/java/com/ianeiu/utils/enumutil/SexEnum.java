package com.ianeiu.utils.enumutil;

public enum SexEnum implements EnumMessage {
  MAN("M", "男"),
  WOMAN("F", "女");

  private String code;
  private String desc;

  SexEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public Object getValue() {
    //此处需要根据枚举对象的哪个属性返回枚举对象，就return该属性
    return code;
  }

}