package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class LoginDataDrivenTest {
	@DataProvider(name = "loginData")
	public Object[][] loginData() throws IOException{
		String path=System.getProperty("user.dir")+"/testData/registeredAccounts.xlsx";
		ExcelUtils excelUtils=new ExcelUtils(path);
		int rows;
		try {
			rows = excelUtils.getTotalRows("sheet4");
			int cells=excelUtils.getTotalCells("sheet4");
			String data[][]=new String[rows][cells];
			for (int i = 1; i <=rows; i++) {
				for (int j = 0; j <cells ; j++) {
					data[i-1][j]=excelUtils.getCellData("sheet4", i, j);
				}
			}
			return data;
		} catch (IOException e) {
			return null;
		}
	}

}
