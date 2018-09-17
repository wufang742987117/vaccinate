package com.thinkgem.jeesite.modules.reconciliation.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.reconciliation.entity.ReconciliationStock;
import com.thinkgem.jeesite.modules.reconciliation.service.ReconciliationStockService;
import com.thinkgem.jeesite.modules.reconciliation.vo.RecStockVO;

/**
 * 对帐管理
 * @author zhouqj
 * @date 2017年5月17日 上午11:27:21
 * @description 
 *		TODO
 */
@Controller
@RequestMapping(value = "${adminPath}/reconciliation/reconciliationStock")
public class ReconciliationStockController extends BaseController {

	@Autowired
	private ReconciliationStockService reconciliationStockService;
	
	@ModelAttribute
	public ReconciliationStock get(@RequestParam(required = false) String id) {
		ReconciliationStock entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = reconciliationStockService.get(id);
		}
		if (entity == null) {
			entity = new ReconciliationStock();
		}
		return entity;
	}
	
	@RequestMapping(value = { "list", "" })
	public String list(ReconciliationStock reconciliationStock,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		return "modules/reconciliation/reconciliationStockList";
	}
	
	/** 
	 * 大类疫苗接种完成金额统计
	 * @author zhouqj
	 * @date 2017年5月17日 上午11:34:16
	 * @description 
	 *		TODO
	 * @param reconciliationStock
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("reconciliationStock")
	public String reconciliationStock(ReconciliationStock reconciliationStock, Model model) {
		//根据大类code合并一个集合
		List<ReconciliationStock> list = new ArrayList<>();
		List<ReconciliationStock> listOne = new ArrayList<>();
		List<ReconciliationStock> listTwo = new ArrayList<>();
		//登记台统计金额
		List<ReconciliationStock> list0 = null;
		//微信统计金额
		List<ReconciliationStock> list1 = null;
		//一体机统计金额
		List<ReconciliationStock> list2 = null;
		//微信统计金额(副本)
		List<ReconciliationStock> list11 = new ArrayList<>();
		//一体机统计金额(副本)
		List<ReconciliationStock> list22 = new ArrayList<>();
		//登记台总金额
		double count0 = 0;
		//微信总金额
		double count1 = 0;
		//一体机总金额
		double count2 = 0;
		//全部总金额
		double count3 = 0;
		//根据大类code合并一个集合
		List<ReconciliationStock> relist = new ArrayList<>();
		//疫苗接种完成统计
		List<ReconciliationStock> relist1 = null;
		//疫苗报损统计
		List<ReconciliationStock> relist2 = null;
		//疫苗报损统计(副本)
		List<ReconciliationStock> relist21 = new ArrayList<>();
		//获取时间
		Calendar a = Calendar.getInstance();
		if(StringUtils.isNotBlank(reconciliationStock.getTimeYear()) && StringUtils.isNotBlank(reconciliationStock.getTimeMoon())){
			if(StringUtils.isNumeric(reconciliationStock.getTimeYear())){
				if(reconciliationStock.getTimeYear().length() == 4 && reconciliationStock.getTimeMoon().length() != 0){
					//获取开始与截止时间
					String beginTime = reconciliationStockService.getFirstDayOfMonth(Integer.valueOf(reconciliationStock.getTimeYear()), Integer.valueOf(reconciliationStock.getTimeMoon())-1);
					String endTime = reconciliationStockService.getLastDayOfMonth(Integer.valueOf(reconciliationStock.getTimeYear()), Integer.valueOf(reconciliationStock.getTimeMoon())-1);
					reconciliationStock.setBeginTime(beginTime);
					reconciliationStock.setEndTime(endTime);
					//获取相应金额统计
					list0 = reconciliationStockService.reconciliationStockQuery0(reconciliationStock);
					list1 = reconciliationStockService.reconciliationStockQuery1(reconciliationStock);
					list2 = reconciliationStockService.reconciliationStockQuery2(reconciliationStock);
					//副本list1
					for(ReconciliationStock r : list1){
						list11.add(r);
					}
					//副本list2
					for(ReconciliationStock r : list2){
						list22.add(r);
					}
					//合并记录（登记台--微信）
					if(list0.size() != 0){
						for(ReconciliationStock rs : list0){
							if(list11.size() != 0){
								for(int i = list11.size()-1 ; i >= 0; i--){
									//判断大类code是否一致
									if(rs.getGnum().equals(list11.get(i).getGnum())){
										rs.setPrice1(list11.get(i).getPrice1());
										list11.remove(i);
										break;
									}else{
										rs.setPrice1("0");
										rs.setPrice2("0");
									}
								}
							}else{
								rs.setPrice1("0");
								rs.setPrice2("0");
							}
							listOne.add(rs);
						}
						for(ReconciliationStock rs : list11){
							rs.setPrice("0");
							rs.setPrice2("0");
							listOne.add(rs);
						}
					}else{
						if(list1.size() != 0){
							for(ReconciliationStock rs : list1){
								rs.setPrice("0");
								rs.setPrice2("0");
								listOne.add(rs);
							}
						}
					}
					//合并（登记台、微信---一体机）
					if(listOne.size() != 0){
						for(ReconciliationStock rs : listOne){
							for(int i = list22.size()-1 ; i >= 0; i--){
								//判断大类code是否一致
								if(rs.getGnum().equals(list22.get(i).getGnum())){
									rs.setPrice2(list22.get(i).getPrice2());
									list22.remove(i);
									break;
								}
							}	
							listTwo.add(rs);
						}
						for(ReconciliationStock rs : list22){
							rs.setPrice("0");
							rs.setPrice1("0");
							listTwo.add(rs);
						}
					}else{
						if(list2.size() != 0){
							for(ReconciliationStock rs : list2){
								rs.setPrice("0");
								rs.setPrice1("0");
								listTwo.add(rs);
							}
						}
					}
					//合计该种疫苗总金额
					if(listTwo.size() != 0){
						for(ReconciliationStock rs : listTwo){
							//统计该种疫苗总金额
							rs.setPricesum(String.valueOf((Double.parseDouble(rs.getPrice()) + Double.parseDouble(rs.getPrice1()) + Double.parseDouble(rs.getPrice2()))));
							list.add(rs);
						}
					}
					//计算相关合计值
					if(list.size() != 0){
						for(ReconciliationStock rs : list){
							count0 += Double.parseDouble(rs.getPrice());
							count1 += Double.parseDouble(rs.getPrice1());
							count2 += Double.parseDouble(rs.getPrice2());
							count3 += Double.parseDouble(rs.getPricesum());
						}
					}
					
					//获取相应损耗统计
					relist1 = reconciliationStockService.reconciliationStockCount1(reconciliationStock);
					relist2 = reconciliationStockService.reconciliationStockCount2(reconciliationStock);
					//副本relist2
					for(ReconciliationStock rs : relist2){
						relist21.add(rs);
					}
					//合并记录
					if(relist1.size() != 0){
						for(ReconciliationStock rs : relist1){
							for(int i = relist21.size()-1 ; i >= 0; i--){
								//判断大类code是否一致
								if(rs.getGnum().equals(relist21.get(i).getGnum())){
									rs.setCountsum(String.valueOf(Integer.parseInt(rs.getCountsum()) + Integer.parseInt(relist21.get(i).getCountsum())));
									relist21.remove(i);
									break;
								}
							}
							relist.add(rs);
						}
						for(ReconciliationStock rs : relist21){
							relist.add(rs);
						}
					}else{
						if(relist2.size() != 0){
							for(ReconciliationStock rs : relist2){
								relist.add(rs);
							}
						}
					}
				}else{
					reconciliationStock.setTimeYear(String.valueOf(a.get(Calendar.YEAR)));
					model.addAttribute("arg", "你输入的年或者月份有误！");
				}
			}else{
				reconciliationStock.setTimeYear(String.valueOf(a.get(Calendar.YEAR)));
				model.addAttribute("arg", "你输入的年或者月份有误！");
			}
		}else{
			reconciliationStock.setTimeYear(String.valueOf(a.get(Calendar.YEAR)));
			reconciliationStock.setTimeMoon(String.valueOf(a.get(Calendar.MONTH) + 1));
			if(StringUtils.isNumeric(reconciliationStock.getTimeYear())){
				if(reconciliationStock.getTimeYear().length() == 4 && reconciliationStock.getTimeMoon().length() != 0){
					//获取开始与截止时间
					String beginTime = reconciliationStockService.getFirstDayOfMonth(Integer.valueOf(reconciliationStock.getTimeYear()), Integer.valueOf(reconciliationStock.getTimeMoon())-1);
					String endTime = reconciliationStockService.getLastDayOfMonth(Integer.valueOf(reconciliationStock.getTimeYear()), Integer.valueOf(reconciliationStock.getTimeMoon())-1);
					reconciliationStock.setBeginTime(beginTime);
					reconciliationStock.setEndTime(endTime);
					//获取相应金额统计
					list0 = reconciliationStockService.reconciliationStockQuery0(reconciliationStock);
					list1 = reconciliationStockService.reconciliationStockQuery1(reconciliationStock);
					list2 = reconciliationStockService.reconciliationStockQuery2(reconciliationStock);
					//副本list1
					for(ReconciliationStock r : list1){
						list11.add(r);
					}
					//副本list2
					for(ReconciliationStock r : list2){
						list22.add(r);
					}
					//合并记录（登记台--微信）
					if(list0.size() != 0){
						for(ReconciliationStock rs : list0){
							if(list11.size() != 0){
								for(int i = list11.size()-1 ; i >= 0; i--){
									//判断大类code是否一致
									if(rs.getGnum().equals(list11.get(i).getGnum())){
										rs.setPrice1(list11.get(i).getPrice1());
										list11.remove(i);
										break;
									}else{
										rs.setPrice1("0");
										rs.setPrice2("0");
									}
								}
							}else{
								rs.setPrice1("0");
								rs.setPrice2("0");
							}
							listOne.add(rs);
						}
						for(ReconciliationStock rs : list11){
							rs.setPrice("0");
							rs.setPrice2("0");
							listOne.add(rs);
						}
					}else{
						if(list1.size() != 0){
							for(ReconciliationStock rs : list1){
								rs.setPrice("0");
								rs.setPrice2("0");
								listOne.add(rs);
							}
						}
					}
					//合并（登记台、微信---一体机）
					if(listOne.size() != 0){
						for(ReconciliationStock rs : listOne){
							for(int i = list22.size()-1 ; i >= 0; i--){
								//判断大类code是否一致
								if(rs.getGnum().equals(list22.get(i).getGnum())){
									rs.setPrice2(list22.get(i).getPrice2());
									list22.remove(i);
									break;
								}
							}	
							listTwo.add(rs);
						}
						for(ReconciliationStock rs : list22){
							rs.setPrice("0");
							rs.setPrice1("0");
							listTwo.add(rs);
						}
					}else{
						if(list2.size() != 0){
							for(ReconciliationStock rs : list2){
								rs.setPrice("0");
								rs.setPrice1("0");
								listTwo.add(rs);
							}
						}
					}
					//合计该种疫苗总金额
					if(listTwo.size() != 0){
						for(ReconciliationStock rs : listTwo){
							//统计该种疫苗总金额
							rs.setPricesum(String.valueOf((Double.parseDouble(rs.getPrice()) + Double.parseDouble(rs.getPrice1()) + Double.parseDouble(rs.getPrice2()))));
							list.add(rs);
						}
					}
					//计算相关合计值
					if(list.size() != 0){
						for(ReconciliationStock rs : list){
							count0 += Double.parseDouble(rs.getPrice());
							count1 += Double.parseDouble(rs.getPrice1());
							count2 += Double.parseDouble(rs.getPrice2());
							count3 += Double.parseDouble(rs.getPricesum());
						}
					}
					
					//获取相应损耗统计
					relist1 = reconciliationStockService.reconciliationStockCount1(reconciliationStock);
					relist2 = reconciliationStockService.reconciliationStockCount2(reconciliationStock);
					//副本relist2
					for(ReconciliationStock rs : relist2){
						relist21.add(rs);
					}
					//合并记录
					if(relist1.size() != 0){
						for(ReconciliationStock rs : relist1){
							for(int i = relist21.size()-1 ; i >= 0; i--){
								//判断大类code是否一致
								if(rs.getGnum().equals(relist21.get(i).getGnum())){
									rs.setCountsum(String.valueOf(Integer.parseInt(rs.getCountsum()) + Integer.parseInt(relist21.get(i).getCountsum())));
									relist21.remove(i);
									break;
								}
							}
							relist.add(rs);
						}
						for(ReconciliationStock rs : relist21){
							relist.add(rs);
						}
					}else{
						if(relist2.size() != 0){
							for(ReconciliationStock rs : relist2){
								relist.add(rs);
							}
						}
					}
				}else{
					reconciliationStock.setTimeYear(String.valueOf(a.get(Calendar.YEAR)));
					model.addAttribute("arg", "你输入的年或者月份有误！");
				}
			}else{
				reconciliationStock.setTimeYear(String.valueOf(a.get(Calendar.YEAR)));
				model.addAttribute("arg", "你输入的年或者月份有误！");
			}
		}
		List<RecStockVO> mapKey = new ArrayList<>();
		List<String> mapName = new ArrayList<>();
		for(ReconciliationStock rs : relist){
			mapKey.add(new RecStockVO(rs.getCountsum(), rs.getGname()));
			mapName.add(rs.getGname());
		}
		
		model.addAttribute("list", list);
		model.addAttribute("mapKey", JsonMapper.toJsonString(mapKey)
				.replaceAll("\"", "\'")
				.replaceAll("\'value\':\'", "value:")
				.replaceAll("\',\'", ",")
				.replaceAll("\':", ":"));
		model.addAttribute("mapName", JsonMapper.toJsonString(mapName).replaceAll("\"", "\'"));
		model.addAttribute("count0", String.valueOf(count0));
		model.addAttribute("count1", String.valueOf(count1));
		model.addAttribute("count2", String.valueOf(count2));
		model.addAttribute("count3", String.valueOf(count3));
		model.addAttribute("reconciliationStock", reconciliationStock);
		return "modules/reconciliation/reconciliationStock";
	}
	
}