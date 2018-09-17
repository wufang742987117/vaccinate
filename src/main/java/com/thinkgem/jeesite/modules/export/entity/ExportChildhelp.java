package com.thinkgem.jeesite.modules.export.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class ExportChildhelp extends DataEntity<ExportChildhelp> {

	private static final long serialVersionUID = 1L;

	private String reside;// 居住类型
	private String display;// 显示类型
	private String area;// 所属社区
	private String name;// 社区名称
	private String code;// 社区编码
	private String startDate;// 开始时间
	private String endDate;// 结束时间
	private int numAll;// 儿童总数
	private int numNew;// 新生儿童
	private String startTime;
	private String endTime;

	public String getReside() {
		return reside;
	}

	public void setReside(String reside) {
		this.reside = reside;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getNumAll() {
		return numAll;
	}

	public void setNumAll(int numAll) {
		this.numAll = numAll;
	}

	public int getNumNew() {
		return numNew;
	}

	public void setNumNew(int numNew) {
		this.numNew = numNew;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	
	
	
	
	
	
	
	
	private String codeall;
	private String bcgnum; // BCG 卡介苗
	private String opv1num; // OPV-hdc 脊灰 减毒二倍体 针次1 脊髓灰质炎减毒疫苗糖丸（人二倍体细胞）
							// 脊灰糖丸(二倍体)
	private String opv2num; // OPV-hdc 减毒二倍体 针次2
	private String opv3num; // OPV-hdc 减毒二倍体 针次3
	private String dpt1num; // DPT 百白破疫苗 针刺1 数据库中是 * DTP
	private String dpt2num; // DPT 百白破疫苗 针刺2
	private String dpt3num; // DPT 百白破疫苗 针刺3
	private String mvnum; // MV 麻疹减毒活疫苗
	private String enmvnum; // MV 麻疹减毒活疫苗 增强
	private String hepb1num; // HepB-yst 针次1 * 数据库对应的是HepB-scy
	private String hepb2num; // HepB-yst 针次2
	private String hepb3num; // HepB-yst 针次3
	private String jeinum; // *JE-i 数据库对应JEV-I-Vero
	private String jei1num; // 针次1 *JEV 乙脑疫苗
	private String jei2num; // 针次2
	private String dtnum; // 白破疫苗 DT 不分针次
	private String endtnum; // 白破疫苗 DT 不分针次 增强
	private String dptnum; // 吸附白喉破伤风联合疫苗 不分针次
	private String endptnum; // 吸附白喉破伤风联合疫苗 增强
	private String opvnum; // OPV-hdc 不分针次
	private String enopvnum; // OPV-hdc 不分针次 增强
	private String rubhdcnum;// 风疹减毒活疫苗（人二倍体细胞）*数据库对应rv 1101 风疹疫苗 不分针次
	private String menA1num; // 针次1 流脑A疫苗 s数据库对应MPV
	private String menA2num;// 针次2 流脑A疫苗 s数据库对应MPV
	private String menAnum;// 流脑A 不分针次
	private String enmenA1num; // 针次1 增强 流脑A疫苗 s数据库对应MPV
	private String enmenA2num;// 针次2 增强 流脑A疫苗 s数据库对应MPV
	private String mumnum; // 腮腺炎疫苗 Mum * 数据库对应的是 MuV 不分针次
	private String enjei1num; // 针次1 增强 *JEV 乙脑疫苗
	private String enjei2num; // 针次2 增强
	private String subtotal;// 3-1-1报表的疫苗基础接种小计
	private String subtotal12;// 3-1-1报表的疫苗基础接种小于12月龄的小计
	private String subtotal5;// 3-1-1报表的疫苗加强接种小计

	private String vaccName; // 疫苗名称
	private String dosage;// 疫苗接种针次
	private String vaccineid;// 疫苗id
	private Integer leng;

	private String opv1num12; // OPV-hdc 脊灰 减毒二倍体 <12月龄 针次1 脊髓灰质炎减毒疫苗糖丸（人二倍体细胞）
								// 脊灰糖丸(二倍体)
	private String opv2num12; // OPV-hdc 减毒二倍体 <12月龄 针次2
	private String opv3num12; // OPV-hdc 减毒二倍体 <12月龄 针次3
	private String enopv1num; // OPV-hdc 脊灰 减毒二倍体 加强 针次1 脊髓灰质炎减毒疫苗糖丸（人二倍体细胞）
								// 脊灰糖丸(二倍体)
	private String enopv2num; // OPV-hdc 减毒二倍体 加强 针次2
	private String enopv3num; // OPV-hdc 减毒二倍体 加强 针次3

	private String dpt1num12; // DPT 百白破疫苗 <12月龄 针刺1 数据库中是 * DTP
	private String dpt2num12; // DPT 百白破疫苗 <12月龄 针刺2
	private String dpt3num12; // DPT 百白破疫苗 <12月龄 针刺3
	private String endpt1num; // DPT 百白破疫苗 加强 针刺1 数据库中是 * DTP
	private String endpt2num; // DPT 百白破疫苗 加强 针刺2
	private String endpt3num; // DPT 百白破疫苗 加强 针刺3

	private String dt1num;// 白破疫苗 DT 针次1
	private String dt1num12;// 白破疫苗 DT <12月龄 针次1
	private String endt1num;// 白破疫苗 DT 加强 针次1

	private String hepb1num12; // HepB-yst <12月龄 针次1 * 数据库对应的是HepB-scy 酵母
	private String hepb2num12; // HepB-yst <12月龄 针次2
	private String hepb3num12; // HepB-yst <12月龄 针次3
	private String enhepb1num; // HepB-yst 加强 针次1 * 数据库对应的是HepB-scy
	private String enhepb2num; // HepB-yst 加强 针次2
	private String enhepb3num; // HepB-yst 加强 针次3

	private String jei1num12; // 针次1 <12月龄 *JEV 乙脑疫苗
	private String jei2num12; // 针次2 <12月龄

	private String rubhdc1num;// 风疹减毒活疫苗（人二倍体细胞） 针次1 *数据库对应rv 1101 风疹疫苗
	private String rubhdc1num12;// 风疹减毒活疫苗（人二倍体细胞）<12月龄 针次1
	private String enrubhdc1num;// 风疹减毒活疫苗（人二倍体细胞） 加强 针次1

	private String mum1num; // 腮腺炎疫苗 Mum 针次1 * 数据库对应的是 MuV
	private String mum1num12; // 腮腺炎疫苗 Mum <12月龄 针次1
	private String enmum1num; // 腮腺炎疫苗 Mum 增强 针次1

	private String menA1num12; // 针次1 <12月龄 流脑A疫苗

	private String mv1num; // MV 针次1 麻疹减毒活疫苗
	private String mv1num12; // MV 针次1 <12月龄 麻疹减毒活疫苗
	private String enmv1num; // MV 针次1 增强 麻疹减毒活疫苗

	private String bcg1num;
	private String bcg1num12;
	private String enbcg1num;

	public String getBcg1num() {
		return bcg1num;
	}

	public void setBcg1num(String bcg1num) {
		this.bcg1num = bcg1num;
	}

	public String getBcg1num12() {
		return bcg1num12;
	}

	public void setBcg1num12(String bcg1num12) {
		this.bcg1num12 = bcg1num12;
	}

	public String getEnbcg1num() {
		return enbcg1num;
	}

	public void setEnbcg1num(String enbcg1num) {
		this.enbcg1num = enbcg1num;
	}

	private String hepb;// 乙肝
	private String bcg;// 卡介苗
	private String pv;// 脊灰
	private String dtp;// 百白破
	private String dt;// 白破
	private String mr;// 麻风
	private String mm;// 麻腮
	private String mmr;// 麻腮风
	private String mv;// 麻疹
	private String mpv;// 流脑A
	private String mpvc;// 流脑A+c
	private String jevl;// 乙脑减毒
	private String jevi_Vero;// 乙脑灭活
	private String hepal;// 甲肝减毒
	private String hepai;// 甲肝灭活

	public String getCodeall() {
		return codeall;
	}

	public void setCodeall(String codeall) {
		this.codeall = codeall;
	}

	public String getBcgnum() {
		return bcgnum;
	}

	public void setBcgnum(String bcgnum) {
		this.bcgnum = bcgnum;
	}

	public String getOpv1num() {
		return opv1num;
	}

	public void setOpv1num(String opv1num) {
		this.opv1num = opv1num;
	}

	public String getOpv2num() {
		return opv2num;
	}

	public void setOpv2num(String opv2num) {
		this.opv2num = opv2num;
	}

	public String getOpv3num() {
		return opv3num;
	}

	public void setOpv3num(String opv3num) {
		this.opv3num = opv3num;
	}

	public String getDpt1num() {
		return dpt1num;
	}

	public void setDpt1num(String dpt1num) {
		this.dpt1num = dpt1num;
	}

	public String getDpt2num() {
		return dpt2num;
	}

	public void setDpt2num(String dpt2num) {
		this.dpt2num = dpt2num;
	}

	public String getDpt3num() {
		return dpt3num;
	}

	public void setDpt3num(String dpt3num) {
		this.dpt3num = dpt3num;
	}

	public String getHepb1num() {
		return hepb1num;
	}

	public void setHepb1num(String hepb1num) {
		this.hepb1num = hepb1num;
	}

	public String getHepb2num() {
		return hepb2num;
	}

	public void setHepb2num(String hepb2num) {
		this.hepb2num = hepb2num;
	}

	public String getHepb3num() {
		return hepb3num;
	}

	public void setHepb3num(String hepb3num) {
		this.hepb3num = hepb3num;
	}

	public String getJeinum() {
		return jeinum;
	}

	public void setJeinum(String jeinum) {
		this.jeinum = jeinum;
	}

	public String getVaccName() {
		return vaccName;
	}

	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getMvnum() {
		return mvnum;
	}

	public void setMvnum(String mvnum) {
		this.mvnum = mvnum;
	}

	public String getJei1num() {
		return jei1num;
	}

	public void setJei1num(String jei1num) {
		this.jei1num = jei1num;
	}

	public String getJei2num() {
		return jei2num;
	}

	public void setJei2num(String jei2num) {
		this.jei2num = jei2num;
	}

	public String getDtnum() {
		return dtnum;
	}

	public void setDtnum(String dtnum) {
		this.dtnum = dtnum;
	}

	public String getDptnum() {
		return dptnum;
	}

	public void setDptnum(String dptnum) {
		this.dptnum = dptnum;
	}

	public String getOpvnum() {
		return opvnum;
	}

	public void setOpvnum(String opvnum) {
		this.opvnum = opvnum;
	}

	public String getRubhdcnum() {
		return rubhdcnum;
	}

	public void setRubhdcnum(String rubhdcnum) {
		this.rubhdcnum = rubhdcnum;
	}

	public String getMenA1num() {
		return menA1num;
	}

	public void setMenA1num(String menA1num) {
		this.menA1num = menA1num;
	}

	public String getMenA2num() {
		return menA2num;
	}

	public void setMenA2num(String menA2num) {
		this.menA2num = menA2num;
	}

	public String getEnmenA1num() {
		return enmenA1num;
	}

	public void setEnmenA1num(String enmenA1num) {
		this.enmenA1num = enmenA1num;
	}

	public String getEnmenA2num() {
		return enmenA2num;
	}

	public void setEnmenA2num(String enmenA2num) {
		this.enmenA2num = enmenA2num;
	}

	public String getMumnum() {
		return mumnum;
	}

	public void setMumnum(String mumnum) {
		this.mumnum = mumnum;
	}

	public String getEnjei1num() {
		return enjei1num;
	}

	public void setEnjei1num(String enjei1num) {
		this.enjei1num = enjei1num;
	}

	public String getEnjei2num() {
		return enjei2num;
	}

	public void setEnjei2num(String enjei2num) {
		this.enjei2num = enjei2num;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public String getSubtotal12() {
		return subtotal12;
	}

	public void setSubtotal12(String subtotal12) {
		this.subtotal12 = subtotal12;
	}

	public String getSubtotal5() {
		return subtotal5;
	}

	public void setSubtotal5(String subtotal5) {
		this.subtotal5 = subtotal5;
	}

	public String getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}

	public Integer getLeng() {
		return leng;
	}

	public void setLeng(Integer leng) {
		this.leng = leng;
	}

	public String getMenAnum() {
		return menAnum;
	}

	public void setMenAnum(String menAnum) {
		this.menAnum = menAnum;
	}

	public String getOpv1num12() {
		return opv1num12;
	}

	public void setOpv1num12(String opv1num12) {
		this.opv1num12 = opv1num12;
	}

	public String getOpv2num12() {
		return opv2num12;
	}

	public void setOpv2num12(String opv2num12) {
		this.opv2num12 = opv2num12;
	}

	public String getOpv3num12() {
		return opv3num12;
	}

	public void setOpv3num12(String opv3num12) {
		this.opv3num12 = opv3num12;
	}

	public String getEnopv1num() {
		return enopv1num;
	}

	public void setEnopv1num(String enopv1num) {
		this.enopv1num = enopv1num;
	}

	public String getEnopv2num() {
		return enopv2num;
	}

	public void setEnopv2num(String enopv2num) {
		this.enopv2num = enopv2num;
	}

	public String getEnopv3num() {
		return enopv3num;
	}

	public void setEnopv3num(String enopv3num) {
		this.enopv3num = enopv3num;
	}

	public String getDpt1num12() {
		return dpt1num12;
	}

	public void setDpt1num12(String dpt1num12) {
		this.dpt1num12 = dpt1num12;
	}

	public String getDpt2num12() {
		return dpt2num12;
	}

	public void setDpt2num12(String dpt2num12) {
		this.dpt2num12 = dpt2num12;
	}

	public String getDpt3num12() {
		return dpt3num12;
	}

	public void setDpt3num12(String dpt3num12) {
		this.dpt3num12 = dpt3num12;
	}

	public String getEndpt1num() {
		return endpt1num;
	}

	public void setEndpt1num(String endpt1num) {
		this.endpt1num = endpt1num;
	}

	public String getEndpt2num() {
		return endpt2num;
	}

	public void setEndpt2num(String endpt2num) {
		this.endpt2num = endpt2num;
	}

	public String getEndpt3num() {
		return endpt3num;
	}

	public void setEndpt3num(String endpt3num) {
		this.endpt3num = endpt3num;
	}

	public String getDt1num() {
		return dt1num;
	}

	public void setDt1num(String dt1num) {
		this.dt1num = dt1num;
	}

	public String getDt1num12() {
		return dt1num12;
	}

	public void setDt1num12(String dt1num12) {
		this.dt1num12 = dt1num12;
	}

	public String getEndt1num() {
		return endt1num;
	}

	public void setEndt1num(String endt1num) {
		this.endt1num = endt1num;
	}

	public String getHepb1num12() {
		return hepb1num12;
	}

	public void setHepb1num12(String hepb1num12) {
		this.hepb1num12 = hepb1num12;
	}

	public String getHepb2num12() {
		return hepb2num12;
	}

	public void setHepb2num12(String hepb2num12) {
		this.hepb2num12 = hepb2num12;
	}

	public String getHepb3num12() {
		return hepb3num12;
	}

	public void setHepb3num12(String hepb3num12) {
		this.hepb3num12 = hepb3num12;
	}

	public String getEnhepb1num() {
		return enhepb1num;
	}

	public void setEnhepb1num(String enhepb1num) {
		this.enhepb1num = enhepb1num;
	}

	public String getEnhepb2num() {
		return enhepb2num;
	}

	public void setEnhepb2num(String enhepb2num) {
		this.enhepb2num = enhepb2num;
	}

	public String getEnhepb3num() {
		return enhepb3num;
	}

	public void setEnhepb3num(String enhepb3num) {
		this.enhepb3num = enhepb3num;
	}

	public String getJei1num12() {
		return jei1num12;
	}

	public void setJei1num12(String jei1num12) {
		this.jei1num12 = jei1num12;
	}

	public String getJei2num12() {
		return jei2num12;
	}

	public void setJei2num12(String jei2num12) {
		this.jei2num12 = jei2num12;
	}

	public String getRubhdc1num() {
		return rubhdc1num;
	}

	public void setRubhdc1num(String rubhdc1num) {
		this.rubhdc1num = rubhdc1num;
	}

	public String getRubhdc1num12() {
		return rubhdc1num12;
	}

	public void setRubhdc1num12(String rubhdc1num12) {
		this.rubhdc1num12 = rubhdc1num12;
	}

	public String getEnrubhdc1num() {
		return enrubhdc1num;
	}

	public void setEnrubhdc1num(String enrubhdc1num) {
		this.enrubhdc1num = enrubhdc1num;
	}

	public String getMum1num() {
		return mum1num;
	}

	public void setMum1num(String mum1num) {
		this.mum1num = mum1num;
	}

	public String getMum1num12() {
		return mum1num12;
	}

	public void setMum1num12(String mum1num12) {
		this.mum1num12 = mum1num12;
	}

	public String getEnmum1num() {
		return enmum1num;
	}

	public void setEnmum1num(String enmum1num) {
		this.enmum1num = enmum1num;
	}

	public String getMenA1num12() {
		return menA1num12;
	}

	public void setMenA1num12(String menA1num12) {
		this.menA1num12 = menA1num12;
	}

	public String getMv1num() {
		return mv1num;
	}

	public void setMv1num(String mv1num) {
		this.mv1num = mv1num;
	}

	public String getMv1num12() {
		return mv1num12;
	}

	public void setMv1num12(String mv1num12) {
		this.mv1num12 = mv1num12;
	}

	public String getEnmv1num() {
		return enmv1num;
	}

	public void setEnmv1num(String enmv1num) {
		this.enmv1num = enmv1num;
	}

	public String getEnmvnum() {
		return enmvnum;
	}

	public void setEnmvnum(String enmvnum) {
		this.enmvnum = enmvnum;
	}

	public String getEndtnum() {
		return endtnum;
	}

	public void setEndtnum(String endtnum) {
		this.endtnum = endtnum;
	}

	public String getEndptnum() {
		return endptnum;
	}

	public void setEndptnum(String endptnum) {
		this.endptnum = endptnum;
	}

	public String getEnopvnum() {
		return enopvnum;
	}

	public void setEnopvnum(String enopvnum) {
		this.enopvnum = enopvnum;
	}

	public String getHepb() {
		return hepb;
	}

	public void setHepb(String hepb) {
		this.hepb = hepb;
	}

	public String getBcg() {
		return bcg;
	}

	public void setBcg(String bcg) {
		this.bcg = bcg;
	}

	public String getPv() {
		return pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	public String getDtp() {
		return dtp;
	}

	public void setDtp(String dtp) {
		this.dtp = dtp;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public String getMr() {
		return mr;
	}

	public void setMr(String mr) {
		this.mr = mr;
	}

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getMmr() {
		return mmr;
	}

	public void setMmr(String mmr) {
		this.mmr = mmr;
	}

	public String getMv() {
		return mv;
	}

	public void setMv(String mv) {
		this.mv = mv;
	}

	public String getMpv() {
		return mpv;
	}

	public void setMpv(String mpv) {
		this.mpv = mpv;
	}

	public String getMpvc() {
		return mpvc;
	}

	public void setMpvc(String mpvc) {
		this.mpvc = mpvc;
	}

	public String getJevl() {
		return jevl;
	}

	public void setJevl(String jevl) {
		this.jevl = jevl;
	}

	public String getJevi_Vero() {
		return jevi_Vero;
	}

	public void setJevi_Vero(String jevi_Vero) {
		this.jevi_Vero = jevi_Vero;
	}

	public String getHepal() {
		return hepal;
	}

	public void setHepal(String hepal) {
		this.hepal = hepal;
	}

	public String getHepai() {
		return hepai;
	}

	public void setHepai(String hepai) {
		this.hepai = hepai;
	}

}
