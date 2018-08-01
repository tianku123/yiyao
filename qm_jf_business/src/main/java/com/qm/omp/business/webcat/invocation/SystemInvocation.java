package com.qm.omp.business.webcat.invocation;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.qm.common.util.RequestUtil;
import com.qm.common.util.SessionUtil;
import com.qm.omp.api.constants.RequestConstants;
import com.qm.omp.business.pojo.admin.MenuInfoBean;
import com.qm.omp.business.pojo.admin.UserInfoBean;
import com.qm.omp.business.pojo.drug.City;
import com.qm.omp.business.pojo.drug.Company;
import com.qm.omp.business.pojo.drug.Department;
import com.qm.omp.business.pojo.drug.DrugIntro;
import com.qm.omp.business.pojo.drug.DrugOnly;
import com.qm.omp.business.pojo.drug.DrugPrinter;
import com.qm.omp.business.pojo.drug.WareHouse;
import com.qm.omp.business.pojo.system.RoleInfoBean;
import com.qm.omp.business.service.iface.admin.IMenuService;
import com.qm.omp.business.service.iface.system.IRoleService;
import com.qm.omp.business.service.iface.system.IUserService;
import com.qm.omp.business.service.impl.drug.CityServiceImpl;
import com.qm.omp.business.service.impl.drug.CompanyServiceImpl;
import com.qm.omp.business.service.impl.drug.DepartmentServiceImpl;
import com.qm.omp.business.service.impl.drug.DrugIntroServiceImpl;
import com.qm.omp.business.service.impl.drug.DrugOnlyServiceImpl;
import com.qm.omp.business.service.impl.drug.DrugPrinterServiceImpl;
import com.qm.omp.business.service.impl.drug.DrugServiceImpl;
import com.qm.omp.business.service.impl.drug.OrderDetailServiceImpl;
import com.qm.omp.business.service.impl.drug.OrderServiceImpl;
import com.qm.omp.business.service.impl.drug.WareHouseServiceImpl;
import com.qm.omp.business.service.impl.export.ExportServiceImpl;
import com.qm.omp.business.timer.StockBalanceJob;
import com.qm.omp.business.util.DateTimeUtil;

/**
 * 原生态的springMVC的controller类
 * 
 * @author rh
 * @date 2014-12-16 15:50:02
 * 
 */
@Controller
@RequestMapping("system")
public class SystemInvocation {

	private final static Logger alipayLogger = Logger.getLogger("alipayLogger");

	// 当前日期
	private String yyyyMMdd = DateTimeUtil.getTodayChar8();

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IMenuService funcQXService;

	@Autowired
	private CityServiceImpl cityService;

	@Autowired
	private WareHouseServiceImpl wareHouseService;

	@Autowired
	private DrugServiceImpl drugService;

	@Autowired
	private DrugOnlyServiceImpl drugOnlyService;

	@Autowired
	private DrugIntroServiceImpl drugIntroService;

	@Autowired
	private DrugPrinterServiceImpl drugPrinterService;

	@Autowired
	private CompanyServiceImpl companyService;

	@Autowired
	private ExportServiceImpl exportService;

	@Autowired
	private DepartmentServiceImpl departmentService;

	@Autowired
	private OrderDetailServiceImpl orderDetailService;

	@Autowired
	private OrderServiceImpl orderService;

	@Autowired
	private StockBalanceJob stockBalancejob;

	/**
	 * 库存每月结余，流转到下月的定时任务
	 */
	@RequestMapping("stockJob")
	@ResponseBody
	public String stockJob() {
		long start = System.currentTimeMillis();
		try {
			stockBalancejob.stock();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "success" + (System.currentTimeMillis() - start);
	}

	/**
	 * 获取药品列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getDrugOnlyList")
	@ResponseBody
	public String getDrugOnlyList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) Integer userId) {
		try {
			List<DrugOnly> user = this.drugOnlyService.getDrugOnlyList(userId);
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(user));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取用户数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getUserList")
	@ResponseBody
	public String getUserList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<UserInfoBean> user = this.userService.getUserList();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(user));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取角色数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getRoleList")
	@ResponseBody
	public String getRoleList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String roleType) {
		try {
			List<RoleInfoBean> user = roleService.getRoleList(roleType);
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(user));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取主管树状结构
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getZhuGuanTree")
	@ResponseBody
	public String getZhuGuanTree(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String roleType) {
		try {
			List<UserInfoBean> users = this.userService.getZhuGuanUser();
			/**
			 * 一级：主管、大区主管 二级：用户
			 */
			List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
			Map<String, Object> dqTreeNode = new LinkedHashMap<String, Object>();
			dqTreeNode.put("id", "daquzhuguan");
			dqTreeNode.put("name", "大区主管");
			// 大区主管用户 start
			List<Map<String, Object>> dqList = new ArrayList<Map<String, Object>>();
			Map<String, Object> dq = new LinkedHashMap<String, Object>();
			for (UserInfoBean user : users) {
				if ("daquzhuguan".equals(user.getfUserRoleRel())) {
					dq = new LinkedHashMap<String, Object>();
					dq.put("name", user.getfUserName());
					dq.put("id", user.getId());
					dq.put("parentId", "daquzhuguan");
					dqList.add(dq);
				}
			}
			dqTreeNode.put("children", dqList);
			// 大区主管用户 end
			tree.add(dqTreeNode);
			// 小区主管用户 start
			Map<String, Object> xqTreeNode = new LinkedHashMap<String, Object>();
			xqTreeNode.put("id", "xiaoquzhuguan");
			xqTreeNode.put("name", "小区主管");
			List<Map<String, Object>> xqList = new ArrayList<Map<String, Object>>();
			Map<String, Object> xq = new LinkedHashMap<String, Object>();
			for (UserInfoBean user : users) {
				if ("xiaoquzhuguan".equals(user.getfUserRoleRel())) {
					xq = new LinkedHashMap<String, Object>();
					xq.put("name", user.getfUserName());
					xq.put("id", user.getId());
					dq.put("parentId", "xiaoquzhuguan");
					xqList.add(xq);
				}
			}
			xqTreeNode.put("children", xqList);
			// 小区主管用户 end
			tree.add(xqTreeNode);
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(tree));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取药品介绍分类
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllDrugIntro")
	@ResponseBody
	public String getAllDrugIntro(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String roleType) {
		try {
			List<DrugIntro> list = this.drugIntroService.getAllBean();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(list));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取药品经营分类
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllDrugPrinter")
	@ResponseBody
	public String getAllDrugPrinter(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String roleType) {
		try {
			List<DrugPrinter> list = this.drugPrinterService.getAllBean();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(list));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取药品经营分类 可多选
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllDrugPrinter2")
	@ResponseBody
	public String getAllDrugPrinter2(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String roleType) {
		try {
			List<DrugPrinter> list = this.drugPrinterService.getAllBean2();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(list));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取医药公司
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllCompany2")
	@ResponseBody
	public String getAllCompany2(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<Company> list = this.companyService.getAllBean2();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(list));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取医药公司 可多选
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllCompany")
	@ResponseBody
	public String getAllCompany(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<Company> list = this.companyService.getAllBean();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(list));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取药品经营分类 可多选 已选
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllDrugPrinter2_checked")
	@ResponseBody
	public String getAllDrugPrinter2_checked(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) Integer customerId) {
		try {
			List<DrugPrinter> list = this.drugPrinterService
					.getAllBean(customerId);
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(list));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取药品公司 可多选 已选
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllCompany_checked")
	@ResponseBody
	public String getAllCompany_checked(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) Integer customerId) {
		try {
			List<Company> list = this.companyService.getAllBean(customerId);
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(list));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取店铺区域
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllCity")
	@ResponseBody
	public String getAllCity(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String roleType) {
		try {
			List<City> list = this.cityService.getAllBean();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(list));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取部门树
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllDepartment")
	@ResponseBody
	public String getAllDepartment(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String roleType) {
		try {
			List<Department> list = this.departmentService.getAllBean();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(list));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取部门树 已勾选
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllDepartment_checked")
	@ResponseBody
	public String getAllDepartment_checked(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String userId) {
		try {
			List<Department> list = this.departmentService.getAllBean(userId);
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(list));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取仓库
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllWarehouse")
	@ResponseBody
	public String getAllWarehouse(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String roleType) {
		try {
			List<WareHouse> list = this.wareHouseService.getAllBean();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(list));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取URL树，用户权限管理模块
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMenuUrlList")
	@ResponseBody
	public String getMenuUrlList(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String roleCode) {
		try {
			List<MenuInfoBean> user = null;
			if (!"999999".equals(roleCode)) {// 全部时不显示树
				user = funcQXService.getMenuUrlList(roleCode);
			}
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(user));
			out.flush();
			out.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取URL树，终端用户权限管理模块
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getMenuUrlList_Terminal")
	@ResponseBody
	public String getMenuUrlList_Terminal(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String roleCode) {
		try {
			List<Map<String, Object>> user = null;
			if (!"999999".equals(roleCode)) {// 全部时不显示树
				user = funcQXService.getMenuUrlList_Terminal(roleCode);
			}
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(user));
			out.flush();
			out.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取支付方式组成条件树 可多选
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getTypeTree")
	@ResponseBody
	public String getTypeTree(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
			Map<String, Object> sgl = null;
			sgl = new LinkedHashMap<String, Object>();
			sgl.put("id", 0);
			sgl.put("text", "现金");
			sgl.put("checked", true);
			children.add(sgl);

			sgl = new LinkedHashMap<String, Object>();
			sgl.put("id", 1);
			sgl.put("text", "支付宝");
			sgl.put("checked", true);
			children.add(sgl);

			sgl = new LinkedHashMap<String, Object>();
			sgl.put("id", 2);
			sgl.put("text", "微信");
			sgl.put("checked", true);
			children.add(sgl);

			Map<String, Object> total = new LinkedHashMap<String, Object>();
			total.put("id", "All");
			total.put("text", "全部");
			total.put("checked", true);
			total.put("children", children);
			res.add(total);
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONArray.toJSONString(res));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 产品介绍分类导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exportDrugIntro")
	@ResponseBody
	public String exportDrugIntro(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String now = DateTimeUtil.getTodayChar12();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attchment;filename="
							+ new String(("药品介绍分类" + now).getBytes("GBK"),
									"ISO-8859-1") + ".xls");
			OutputStream out = response.getOutputStream();
			exportService.exportDrugIntro().write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 药品经营分类导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exportDrugPrinter")
	@ResponseBody
	public String exportDrugPrinter(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String now = DateTimeUtil.getTodayChar12();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attchment;filename="
							+ new String(("药品经营分类" + now).getBytes("GBK"),
									"ISO-8859-1") + ".xls");
			OutputStream out = response.getOutputStream();
			exportService.exportDrugPrinter().write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 仓库导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exportWareHouse")
	@ResponseBody
	public String exportWareHouse(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String now = DateTimeUtil.getTodayChar12();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attchment;filename="
					+ new String(("仓库" + now).getBytes("GBK"), "ISO-8859-1")
					+ ".xls");
			OutputStream out = response.getOutputStream();
			exportService.exportWareHouse().write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 客户区域导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exportCity")
	@ResponseBody
	public String exportCity(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String now = DateTimeUtil.getTodayChar12();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attchment;filename="
					+ new String(("客户区域" + now).getBytes("GBK"), "ISO-8859-1")
					+ ".xls");
			OutputStream out = response.getOutputStream();
			exportService.exportCity().write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 产品介绍导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exportDrugOnlyIntro")
	@ResponseBody
	public String exportDrugOnlyIntro(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String fName = RequestUtil
					.getStrParamterAsDef(request, "fName", "");
			String fDrugPrinterName = RequestUtil.getStrParamterAsDef(request,
					"fDrugPrinterName", "");
			String fDrugIntroName = RequestUtil.getStrParamterAsDef(request,
					"fDrugIntroName", "");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fName", fName);
			params.put("fDrugPrinterName", fDrugPrinterName);
			params.put("fDrugIntroName", fDrugIntroName);
			UserInfoBean userInfo = (UserInfoBean) SessionUtil
					.getObjectAttribute(request,
							RequestConstants.ADMIN_SESSION_KEY);
			params.put("userId", userInfo.getId());
			String now = DateTimeUtil.getTodayChar12();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attchment;filename="
					+ new String(("产品介绍" + now).getBytes("GBK"), "ISO-8859-1")
					+ ".xls");
			OutputStream out = response.getOutputStream();
			exportService.exportDrugOnlyIntro(params).write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 药品导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exportDrugOnly")
	@ResponseBody
	public String exportDrugOnly(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String now = DateTimeUtil.getTodayChar12();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attchment;filename="
					+ new String(("药品资料" + now).getBytes("GBK"), "ISO-8859-1")
					+ ".xls");
			OutputStream out = response.getOutputStream();
			exportService.exportDrugOnly().write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 库存管理导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exportDrug")
	@ResponseBody
	public String exportDrug(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String now = DateTimeUtil.getTodayChar12();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attchment;filename="
					+ new String(("药品库存" + now).getBytes("GBK"), "ISO-8859-1")
					+ ".xls");
			OutputStream out = response.getOutputStream();
			exportService.exportDrug().write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 订单及订单详情导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exportOrderDetail")
	@ResponseBody
	public String exportOrderDetail(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int page = RequestUtil.getIntParamterAsDef(request, "page", 1);
			int rows = RequestUtil.getIntParamterAsDef(request, "rows", 50);
			// 默认状态1，未审核
			String fState = RequestUtil.getStrParamterAsDef(request, "fState",
					"");
			// 付款情况
			String fPaymentState = RequestUtil.getStrParamterAsDef(request,
					"fPaymentState", "");
			// 财务审核情况
			String fExamine = RequestUtil.getStrParamterAsDef(request,
					"fExamine", "");
			// 是否含税
			String fTax = RequestUtil.getStrParamterAsDef(request, "fTax", "");
			// 政策报单
			String fIsPolicy = RequestUtil.getStrParamterAsDef(request,
					"fIsPolicy", "");
			String fName = RequestUtil
					.getStrParamterAsDef(request, "fName", "");
			String fCustomName = RequestUtil.getStrParamterAsDef(request,
					"fCustomName", "");
			String beginTime = RequestUtil.getStrParamterAsDef(request,
					"beginTime", "");
			String endTime = RequestUtil.getStrParamterAsDef(request,
					"endTime", "");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fName", fName);
			params.put("fCustomName", fCustomName);
			params.put("fState", fState);
			params.put("fPaymentState", fPaymentState);
			params.put("fExamine", fExamine);
			params.put("fTax", fTax);
			params.put("fIsPolicy", fIsPolicy);
			params.put("beginTime", DateTimeUtil.get_yyyyMMddHHmmss(beginTime));
			params.put("endTime", DateTimeUtil.get_yyyyMMddHHmmss(endTime));
			String now = DateTimeUtil.getTodayChar12();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attchment;filename="
					+ new String(("订单及订单详情" + now).getBytes("GBK"),
							"ISO-8859-1") + ".xls");
			OutputStream out = response.getOutputStream();
			this.orderDetailService.exportOrderDetail(params).write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出每日库存及每日销售
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exportSalesSummary")
	@ResponseBody
	public String exportSalesSummary(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String fName = RequestUtil
					.getStrParamterAsDef(request, "fName", "");
			String beginTime = RequestUtil.getStrParamterAsDef(request,
					"beginTime", "");
			// ywy 业务员看到的库存， admin 看的信息
			String fType = RequestUtil
					.getStrParamterAsDef(request, "fType", "");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fName", fName);
			params.put("fType", fType);
			params.put("fTime", beginTime.replaceAll("-", ""));
			String now = DateTimeUtil.getTodayChar12();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attchment;filename="
					+ new String(("每日库存及每日销售" + now).getBytes("GBK"),
							"ISO-8859-1") + ".xls");
			OutputStream out = response.getOutputStream();

			if ("admin".equals(fType)) {
				this.orderService.exportSalesSummary(params).write(out);
			} else {
				this.orderService.exportSalesSummary_ywy(params).write(out);
			}
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
