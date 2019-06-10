package poi.excel;


import com.foutin.datastructs.algorithm.checkconnectivity.DFSCheckBetter;
import com.foutin.poi.excel.ExcelImportUtil;
import poi.excel.model.PointDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * FUN Test
 *
 * @author xuxueli 2017-09-08 22:41:19
 */
public class Test {

    public static void main(String[] args) {

        /**
         * Mock数据，Java对象列表
         */
        /*List<ShopDTO> shopDTOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ShopDTO shop = new ShopDTO(true, "商户"+i, (short) i, 1000+i, 10000+i, (float) (1000+i), (double) (10000+i), new Date());
            shopDTOList.add(shop);
        }
        String filePath = "C:/Users/xingkai.fan/Desktop/666.xls";

        *//**
         * Excel导出：Object 转换为 Excel
         *//*
        ExcelExportUtil.exportToFile(filePath, shopDTOList);*/

        // SELECT source_point_ukid,target_point_ukid FROM gs_point_connectivity LIMIT 1000
        long currentTimeMillis = System.currentTimeMillis();
        String filePath1 = "C:/Users/xingkai.fan/Desktop/points.xlsx";
        /**
         * Excel导入：Excel 转换为 Object
         */
        List<Object> list = ExcelImportUtil.importExcel(filePath1, PointDTO.class);

        int size = list.size();
        Long[][] edges = new Long[size][2];
        Set<Long> longSet = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            PointDTO pointDTO = (PointDTO) list.get(i);
            edges[i][0] = pointDTO.getSourcePointUkid();
            edges[i][1] = pointDTO.getTargetPointUkid();
            longSet.add(pointDTO.getSourcePointUkid());
            longSet.add(pointDTO.getTargetPointUkid());
        }

        Object[] vexs = longSet.toArray();

        long millis = System.currentTimeMillis();
        // 深度遍历
        DFSCheckBetter dfsCheckBetter = new DFSCheckBetter();
        dfsCheckBetter.generateTree(vexs, edges);
        dfsCheckBetter.dfs();
        long dfsTimeMillis = System.currentTimeMillis() - millis;
        long allTime = System.currentTimeMillis() - currentTimeMillis;
        System.out.println("深度遍历花费时间：" + dfsTimeMillis);  // 一千个点到点距离 大概花费30-200ms之间
        System.out.println("Excel 转换对象到深度遍历总花费时间：" + allTime);

    }

}
