package com.kbk.fep.mngr.dao.vo;

import java.util.LinkedList;
import java.util.List;

/**
 * 주윤정이 보내주는 인터페이스 ID 별 컬럼정보리스트
 * 
 * @author 20160521
 *
 */
public class FepOmmInqVo {
	
	/**
	 * depth type name length padding align explain main int INPUT_SUB건수 3 0 R SUB1
	 * 영역반복건수 main int OUTPUT_SUB건수 3 0 R SUB2 영역반복건수
	 * 
	 * sub1 int depth 1 0 R sub1 String 데이터유형 10 ' ' 좌 String/Integer/BigDecimal
	 * sub1 String 속성 30 ' ' 좌 sub1 String 속성설명 30 ' ' 좌 sub1 int 길이 5 0 우 소수부있을경우 .
	 * 포함길이임 sub1 int 소수 1 0 우 sub1 String 전각여부 1 ' ' 좌 KSC5601 전각처리 Y, 그외 N sub1
	 * String 정렬 5 ' ' 좌 좌=L, 우=R sub1 String padding 1 ' '
	 * 
	 * sub2 int depth 1 0 R sub2 String 데이터유형 10 ' ' 좌 String/Integer/BigDecimal
	 * sub2 String 속성 30 ' ' 좌 sub2 String 속성설명 30 ' ' 좌 sub2 int 길이 5 0 우 소수부있을경우 .
	 * 포함길이임 sub2 int 소수 1 0 우 sub2 String 전각여부 1 ' ' 좌 KSC5601 전각처리 Y, 그외 N sub2
	 * String 정렬 5 ' ' 좌 좌=L, 우=R sub2 String padding 1 ' '
	 */

	private int input_subcnt;
	private int output_subcnt;
	private List<FepOmmInqRowVo> inputList;
	private List<FepOmmInqRowVo> outputList;

	public FepOmmInqVo(byte[] input) {

		if (input.length < 6) {
			throw new ArrayIndexOutOfBoundsException("적절한 길이가 아닙니다 [" + input.length + "]");
		}

		byte[] b_input_subcnt = new byte[3];
		byte[] b_output_subcnt = new byte[3];

		System.arraycopy(input, 719 + 0, b_input_subcnt, 0, 3);
		System.arraycopy(input, 719 + 3, b_output_subcnt, 0, 3);

		this.input_subcnt = Integer.parseInt(new String(b_input_subcnt));
		this.output_subcnt = Integer.parseInt(new String(b_output_subcnt));

		this.inputList = new LinkedList<FepOmmInqRowVo>();
		this.outputList = new LinkedList<FepOmmInqRowVo>();

		byte[] tempVo = new byte[114];
		FepOmmInqRowVo vo = null;

		for (int i = 0; i < this.input_subcnt; i++) {
			System.arraycopy(input, 719 + 6 + 114 * i, tempVo, 0, tempVo.length);
			vo = new FepOmmInqRowVo(tempVo);
			this.inputList.add(vo);
		}

		for (int j = 0; j < this.output_subcnt; j++) {
			System.arraycopy(input, 719 + 6 + this.input_subcnt * 114 + 114 * j, tempVo, 0, tempVo.length);
			vo = new FepOmmInqRowVo(tempVo);
			this.outputList.add(vo);
		}

	}

	public int getInput_subcnt() {
		return input_subcnt;
	}

	public void setInput_subcnt(int input_subcnt) {
		this.input_subcnt = input_subcnt;
	}

	public int getOutput_subcnt() {
		return output_subcnt;
	}

	public void setOutput_subcnt(int output_subcnt) {
		this.output_subcnt = output_subcnt;
	}

	public List<FepOmmInqRowVo> getInputList() {
		return inputList;
	}

	public void setInputList(List<FepOmmInqRowVo> inputList) {
		this.inputList = inputList;
	}

	public List<FepOmmInqRowVo> getOutputList() {
		return outputList;
	}

	public void setOutputList(List<FepOmmInqRowVo> outputList) {
		this.outputList = outputList;
	}

	@Override
	public String toString() {
		return "FepSimLayoutVo [input_subcnt=" + input_subcnt + ", output_subcnt=" + output_subcnt + ", \ninputList="
				+ inputList + ", \noutputList=" + outputList + "]";
	}

}


