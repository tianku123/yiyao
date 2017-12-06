package com.qm.omp.business.service.impl.export;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.drug.ICityDao;
import com.qm.omp.business.dao.drug.IDrugDao;
import com.qm.omp.business.dao.drug.IDrugIntroDao;
import com.qm.omp.business.dao.drug.IDrugOnlyDao;
import com.qm.omp.business.dao.drug.IDrugPrinterDao;
import com.qm.omp.business.dao.drug.IWareHouseDao;
import com.qm.omp.business.pojo.drug.City;
import com.qm.omp.business.pojo.drug.Drug;
import com.qm.omp.business.pojo.drug.DrugIntro;
import com.qm.omp.business.pojo.drug.DrugOnly;
import com.qm.omp.business.pojo.drug.DrugPrinter;
import com.qm.omp.business.pojo.drug.WareHouse;
import com.qm.omp.business.service.impl.drug.DrugOnlyServiceImpl;
import com.qm.omp.business.util.PoiUtil;

@Service("exportService")
public class ExportServiceImpl {

	@Autowired
	private IDrugIntroDao drugIntroDao;
	
	@Autowired
	private IDrugPrinterDao drugPrinterDao;
	
	@Autowired
	private IWareHouseDao wareHouseDao;
	
	@Autowired
	private ICityDao cityDao;
	
	@Autowired
	private IDrugOnlyDao drugOnlyDao;
	
	@Autowired
	private IDrugDao drugDao;

	@Autowired
	private DrugOnlyServiceImpl drugOnlyService;
	
	public HSSFWorkbook exportDrugIntro() {
		List<DrugIntro> data = this.drugIntroDao.exportAllBean();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("药品介绍分类");
		sheet.setDefaultColumnWidth(15);
		sheet.setColumnWidth(3, 10000);//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
		HSSFCellStyle titleStyle = PoiUtil.getTitleStyle(wb);
		HSSFCellStyle cellStyle = PoiUtil.getCellStyle(wb);
		String[] headers = {"id","名称","状态(0:正常，1:删除)","创建时间"};
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for(int i=0;i<headers.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(titleStyle);
		}
		
		for(int i=0;i<data.size();i++){
			row = sheet.createRow(i+1);
			cell = row.createCell(0);
			cell.setCellValue(data.get(i).getfId());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(data.get(i).getfName());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(2);
			cell.setCellValue(data.get(i).getfState());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(3);
			cell.setCellValue(data.get(i).getfTime());
			cell.setCellStyle(cellStyle);
			
		}
		
		return wb;
	}

	
	public HSSFWorkbook exportDrugPrinter() {
		List<DrugPrinter> data = this.drugPrinterDao.exportAllBean();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("药品经营分类");
		sheet.setDefaultColumnWidth(15);
		sheet.setColumnWidth(3, 10000);//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
		HSSFCellStyle titleStyle = PoiUtil.getTitleStyle(wb);
		HSSFCellStyle cellStyle = PoiUtil.getCellStyle(wb);
		String[] headers = {"id","名称","状态(0:正常，1:删除)","创建时间"};
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for(int i=0;i<headers.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(titleStyle);
		}
		
		for(int i=0;i<data.size();i++){
			row = sheet.createRow(i+1);
			cell = row.createCell(0);
			cell.setCellValue(data.get(i).getfId());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(data.get(i).getfName());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(2);
			cell.setCellValue(data.get(i).getfState());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(3);
			cell.setCellValue(data.get(i).getfTime());
			cell.setCellStyle(cellStyle);
			
		}
		
		return wb;
	}

	
	public HSSFWorkbook exportWareHouse() {
		List<WareHouse> data = this.wareHouseDao.exportAllBean();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("仓库");
		sheet.setDefaultColumnWidth(15);
		sheet.setColumnWidth(4, 10000);//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
		HSSFCellStyle titleStyle = PoiUtil.getTitleStyle(wb);
		HSSFCellStyle cellStyle = PoiUtil.getCellStyle(wb);
		String[] headers = {"id","名称", "地址", "状态(0:正常，1:删除)","创建时间"};
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for(int i=0;i<headers.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(titleStyle);
		}
		
		for(int i=0;i<data.size();i++){
			row = sheet.createRow(i+1);
			cell = row.createCell(0);
			cell.setCellValue(data.get(i).getfId());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(data.get(i).getfName());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(2);
			cell.setCellValue(data.get(i).getfAddress());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(3);
			cell.setCellValue(data.get(i).getfState());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(4);
			cell.setCellValue(data.get(i).getfTime());
			cell.setCellStyle(cellStyle);
			
		}
		
		return wb;
	}

	
	public HSSFWorkbook exportCity() {
		List<City> data = this.cityDao.exportAllBean();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("客户区域");
		sheet.setDefaultColumnWidth(15);
		sheet.setColumnWidth(3, 10000);//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
		HSSFCellStyle titleStyle = PoiUtil.getTitleStyle(wb);
		HSSFCellStyle cellStyle = PoiUtil.getCellStyle(wb);
		String[] headers = {"id","名称","状态(0:正常，1:删除)","创建时间"};
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for(int i=0;i<headers.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(titleStyle);
		}
		
		for(int i=0;i<data.size();i++){
			row = sheet.createRow(i+1);
			cell = row.createCell(0);
			cell.setCellValue(data.get(i).getfId());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(data.get(i).getfName());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(2);
			cell.setCellValue(data.get(i).getfState());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(3);
			cell.setCellValue(data.get(i).getfTime());
			cell.setCellStyle(cellStyle);
			
		}
		return wb;
	}

	
	public HSSFWorkbook exportDrugOnly() {
		List<DrugOnly> data = this.drugOnlyDao.exportAllBean();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("药品");
		sheet.setDefaultColumnWidth(15);
		sheet.setColumnWidth(5, 10000);//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
		HSSFCellStyle titleStyle = PoiUtil.getTitleStyle(wb);
		HSSFCellStyle cellStyle = PoiUtil.getCellStyle(wb);
		String[] headers = {"id","名称","规格","产地","状态(0:正常，1:删除)","创建时间"
				,"供货价","零售价","图片","药品介绍分类id","药品经营分类id","药品介绍"};
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for(int i=0;i<headers.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(titleStyle);
		}
		
		for(int i=0;i<data.size();i++){
			row = sheet.createRow(i+1);
			cell = row.createCell(0);
			cell.setCellValue(data.get(i).getfId());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(data.get(i).getfName());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(2);
			cell.setCellValue(data.get(i).getfSpecification());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(3);
			cell.setCellValue(data.get(i).getfAddress());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(4);
			cell.setCellValue(data.get(i).getfState());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(5);
			cell.setCellValue(data.get(i).getfTime());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(6);
			cell.setCellValue(data.get(i).getfSupplyPrice());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(7);
			cell.setCellValue(data.get(i).getfRetailPrice());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(8);
			cell.setCellValue(data.get(i).getfImg());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(9);
			cell.setCellValue(data.get(i).getfDrugIntroId()+"");
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(10);
			cell.setCellValue(data.get(i).getfDrugPrinterId()+"");
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(11);
			cell.setCellValue(data.get(i).getfIntro());
			cell.setCellStyle(cellStyle);
			
		}
		return wb;
	}

	
	public HSSFWorkbook exportDrug() {
		List<Drug> data = this.drugDao.exportAllBean();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("药品");
		sheet.setDefaultColumnWidth(15);
		sheet.setColumnWidth(5, 10000);//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
		HSSFCellStyle titleStyle = PoiUtil.getTitleStyle(wb);
		HSSFCellStyle cellStyle = PoiUtil.getCellStyle(wb);
		String[] headers = {"id","名称","规格","产地","状态(0:正常，1:删除)","创建时间"
				,"供货价","零售价","图片","药品介绍分类id","药品经营分类id","药品介绍"};
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for(int i=0;i<headers.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(titleStyle);
		}
		
		for(int i=0;i<data.size();i++){
			row = sheet.createRow(i+1);
			cell = row.createCell(0);
			cell.setCellValue(data.get(i).getfId());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(data.get(i).getfName());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(2);
			cell.setCellValue(data.get(i).getfSpecification());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(3);
			cell.setCellValue(data.get(i).getfAddress());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(4);
			cell.setCellValue(data.get(i).getfState());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(5);
			cell.setCellValue(data.get(i).getfTime());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(6);
			cell.setCellValue(data.get(i).getfSupplyPrice());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(7);
			cell.setCellValue(data.get(i).getfRetailPrice());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(8);
			cell.setCellValue(data.get(i).getfImg());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(9);
//			cell.setCellValue(data.get(i).getfDrugIntroId()+"");
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(10);
//			cell.setCellValue(data.get(i).getfDrugPrinterId()+"");
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(11);
			cell.setCellValue(data.get(i).getfIntro());
			cell.setCellStyle(cellStyle);
			
		}
		return wb;
	}

	
	public HSSFWorkbook exportDrugOnlyIntro(Map<String, Object> params) {
		List<DrugOnly> data = (List<DrugOnly>) this.drugOnlyService.getList_DrugOnlyIntro(0, 0, params).get("rows");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("产品介绍");
		sheet.setDefaultColumnWidth(15);
//		sheet.setColumnWidth(3, 10000);//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
		HSSFCellStyle titleStyle = PoiUtil.getTitleStyle(wb);
		HSSFCellStyle cellStyle = PoiUtil.getCellStyle(wb);
		String[] headers = {"产品分类","药品名称","药品规格","药品效期","供货价","零售价","库存","产品介绍"};
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for(int i=0;i<headers.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(titleStyle);
		}
		
		for(int i=0;i<data.size();i++){
			row = sheet.createRow(i+1);
			cell = row.createCell(0);
			cell.setCellValue(data.get(i).getfDrugIntroName());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(data.get(i).getfName());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(2);
			cell.setCellValue(data.get(i).getfSpecification());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(3);
			cell.setCellValue(data.get(i).getfExpiryDate());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(4);
			cell.setCellValue(data.get(i).getfSupplyPrice());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(5);
			cell.setCellValue(data.get(i).getfRetailPrice());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(6);
			cell.setCellValue(data.get(i).getfNumber());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(7);
			cell.setCellValue(data.get(i).getfIntro());
			cell.setCellStyle(cellStyle);
			
		}
		
		return wb;
	}

	
}
