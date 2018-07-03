package com.yun.mayi.pda.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.bean.PackBox;
import com.yun.mayi.pda.bean.PackBoxDetail;
import com.yun.mayi.pda.bean.PickOrder;
import com.yun.mayi.pda.bean.PickPrintData;
import com.yun.mayi.pda.bean.PickPrintDataResult;
import com.yun.mayi.pda.bean.ReceiptPrintInfo;
import com.yun.mayi.pda.db.UserMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import zpSDK.zpSDK.zpBluetoothPrinter;


public class PrintUtils {

    /**
     * 打印纸一行最大的字节
     */
    private static final int LINE_BYTE_SIZE = 20;

    private static final int LEFT_LENGTH = 30;

    private static int RIGHT_LENGTH = 18;

    /**
     * 左侧汉字最多显示几个文字
     */
    private static final int LEFT_TEXT_MAX_LENGTH = 11;

    /**
     * 小票打商品的名称，上限调到8个字
     */
    public static final int MEAL_NAME_MAX_LENGTH = 12;

    /**
     * 规格，上限调到8个字
     */
    public static final int MIDDLE_TEXT_MAX_LENGTH = 10;

    /**
     * 数量，上限调到8=4个字
     */
    public static final int RIGHT_TEXT_MAX_LENGTH = 4;

    private static OutputStream outputStream = null;

    public static OutputStream getOutputStream() {
        return outputStream;
    }

    public static void setOutputStream(OutputStream outputStream) {
        PrintUtils.outputStream = outputStream;
    }


    /**
     * 打印文字
     *
     * @param text 要打印的文字
     */
    public static void printText(String text) {
        try {
            byte[] data = text.getBytes("gbk");
            outputStream.write(data, 0, data.length);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置打印格式
     *
     * @param command 格式指令
     */
    public static void selectCommand(byte[] command) {
        try {
            outputStream.write(command);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复位打印机
     */
    public static final byte[] RESET = {0x1b, 0x40};

    /**
     * 左对齐
     */
    public static final byte[] ALIGN_LEFT = {0x1b, 0x61, 0x00};

    /**
     * 中间对齐
     */
    public static final byte[] ALIGN_CENTER = {0x1b, 0x61, 0x01};

    /**
     * 右对齐
     */
    public static final byte[] ALIGN_RIGHT = {0x1b, 0x61, 0x02};

    /**
     * 选择加粗模式
     */
    public static final byte[] BOLD = {0x1b, 0x45, 0x01};

    /**
     * 取消加粗模式
     */
    public static final byte[] BOLD_CANCEL = {0x1b, 0x45, 0x00};

    /**
     * 宽高加倍
     */
    public static final byte[] DOUBLE_HEIGHT_WIDTH = {0x1d, 0x21, 0x11};

    /**
     * 宽加倍
     */
    public static final byte[] DOUBLE_WIDTH = {0x1d, 0x21, 0x10};

    /**
     * 高加倍
     */
    public static final byte[] DOUBLE_HEIGHT = {0x1d, 0x21, 0x01};

    /**
     * 字体不放大
     */
    public static final byte[] NORMAL = {0x1d, 0x21, 0x00};

    /**
     * 设置默认行间距
     */
    public static final byte[] LINE_SPACING_DEFAULT = {0x1b, 0x32};

    /**
     * 设置默认行扩大间距
     */
    public static final byte[] LINE_SPACING_BIG = {0x1b, 0x32};


    /**
     * 打印两列
     *
     * @param leftText  左侧文字
     * @param rightText 右侧文字
     * @return
     */
    @SuppressLint("NewApi")
    public static String printTwoData(String leftText, String rightText) {
        StringBuilder sb = new StringBuilder();
        int leftTextLength = getBytesLength(leftText);
        int rightTextLength = getBytesLength(rightText);
        sb.append(leftText);

        // 计算两侧文字中间的空格
        int marginBetweenMiddleAndRight = LINE_BYTE_SIZE - leftTextLength - rightTextLength;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }
        sb.append(rightText);
        return sb.toString();
    }


    /**
     * 打印三列
     *
     * @param leftText   左侧文字
     * @param middleText 中间文字
     * @param rightText  右侧文字
     * @return
     */
    @SuppressLint("NewApi")
    public static String printThreeData1(String leftText, String middleText, String rightText) {
        StringBuilder sb = new StringBuilder();
        // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        if (leftText.length() > LEFT_TEXT_MAX_LENGTH) {
            leftText = leftText.substring(0, LEFT_TEXT_MAX_LENGTH);
        }
        if (middleText.length() > MIDDLE_TEXT_MAX_LENGTH) {
            middleText = middleText.substring(0, MIDDLE_TEXT_MAX_LENGTH);
        }
        if (rightText.length() > RIGHT_TEXT_MAX_LENGTH) {
            rightText = rightText.substring(0, RIGHT_TEXT_MAX_LENGTH);
        }
        int leftTextLength = getBytesLength(leftText);
        int middleTextLength = getBytesLength(middleText);
        int rightTextLength = getBytesLength(rightText);

        sb.append(leftText);
        // 计算左侧文字和中间文字的空格长度
        int marginBetweenLeftAndMiddle = LEFT_LENGTH - leftTextLength - middleTextLength / 2;

        for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
            sb.append(" ");

        }
        sb.append(middleText);

        // 计算右侧文字和中间文字的空格长度
        int marginBetweenMiddleAndRight = RIGHT_LENGTH - rightTextLength - middleTextLength / 2;
        G.log("xxxxx:间隔数-----" + marginBetweenMiddleAndRight);
        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length() - 1, sb.length()).append(rightText);


        return sb.toString();
    }

    public static String printThreeData(String leftText, String middleText, String rightText) {
        StringBuilder sb = new StringBuilder();
        // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        int leftTextLength = getBytesLength(leftText);
        int middleTextLength = getBytesLength(middleText);
        int rightTextLength = getBytesLength(rightText);
        sb.append(leftText);
        // 计算左侧文字和中间文字的空格长度
        int marginBetweenLeftAndMiddle = 20 - leftTextLength - middleTextLength / 2;
        for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
            sb.append(" ");
        }
        sb.append(middleText);
        // 计算右侧文字和中间文字的空格长度
        int marginBetweenMiddleAndRight = 20 - rightTextLength - middleTextLength / 2;
        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }
        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length() - 1, sb.length()).append(rightText);
        return sb.toString();
    }

    /**
     * 获取数据长度
     *
     * @param msg
     * @return
     */
    @SuppressLint("NewApi")
    private static int getBytesLength(String msg) {
        return msg.getBytes(Charset.forName("GB2312")).length;
    }

    /**
     * 格式化菜品名称，最多显示MEAL_NAME_MAX_LENGTH个数
     *
     * @param name
     * @return
     */
    public static String formatMealName(String name) {
        if (TextUtils.isEmpty(name)) {
            return name;
        }
        if (name.length() > MEAL_NAME_MAX_LENGTH) {
            return name.substring(0, 8) + "..";
        }
        return name;
    }

    public static boolean print(PackBox packBox) {
        boolean isConnect = YunmayiApplication.connect();
        if (isConnect) {
            PrintUtils.selectCommand(PrintUtils.RESET);
            PrintUtils.selectCommand(PrintUtils.DOUBLE_HEIGHT_WIDTH);
            PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
            PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
            PrintUtils.printText(packBox.getAgentCompany() + " - " + packBox.getOrderMark() + "\n\n");
            PrintUtils.selectCommand(PrintUtils.NORMAL);
            PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
            PrintUtils.printText("\n");
            PrintUtils.printText(packBox.getVendorCompany());
            String currentTime = "      " + DateUtil.getFormatTimeDate(new Date());
            PrintUtils.printText(PrintUtils.printThreeData("sku数：" + packBox.getDetails().size() + "条", "件数：" + packBox.getItemNum() + "件", currentTime + "\n"));
            PrintUtils.printText("------------------------------------------------\n");
            PrintUtils.selectCommand(PrintUtils.BOLD);
            PrintUtils.printText(PrintUtils.printThreeData1("  商品名称", "商品单位", "数量\n\n"));
            PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
            List<PackBoxDetail> packBoxDetails = packBox.getDetails();
            for (int i = 0; i < packBoxDetails.size(); i++) {
                PackBoxDetail detail = packBoxDetails.get(i);
                RIGHT_LENGTH = 15;
                String leftText = detail.getProductTitle();
                PrintUtils.printText(PrintUtils.printThreeData1(leftText, detail.getProductUnit(), detail.getNum() + "\n"));
                //如果标题超过最多的字符就就换行打印
                if (leftText.length() > LEFT_TEXT_MAX_LENGTH) {
                    String nextLine = leftText.substring(LEFT_TEXT_MAX_LENGTH, leftText.length()) + "\n";
                    PrintUtils.printText(nextLine);
                }
            }
            PrintUtils.printText("\n\n\n");
            YunmayiApplication.disconnect();
        }
        return isConnect;
    }
    /**
     * 一维码
     *
     * @param content 一维码的内容
     */
    public static Bitmap createOneDCode(String content) throws WriterException {
        // 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.CODE_128, 500, 72);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
    /**
     * 打印数据
     *
     * @param packBox 装箱数据
     */
    public static boolean printData(PackBox packBox, int totalCount, int index) {
        int line = 0;
        List<PackBoxDetail> packBoxDetails = packBox.getDetails();
        for (int i = 0; i < packBoxDetails.size(); i++) {
            if (packBoxDetails.get(i).getProductTitle().length() > LEFT_TEXT_MAX_LENGTH) {
                line++;  //如果标题超过最多的字符就就换行打印
            }
            line++;
        }
        int between = 36;
        zpBluetoothPrinter zpSDK = new zpBluetoothPrinter(YunmayiApplication.getInstance().getApplicationContext());
        if (YunmayiApplication.bluetoothDevice == null) {
            return false;
        }
        zpSDK.connect(YunmayiApplication.bluetoothDevice.getAddress());
        zpSDK.pageSetup(570, 200 + between * (5 + line));
        String mark = packBox.getAgentCompany() + " - " + packBox.getOrderMark();
        int markX = (570 - G.getTextWidth(mark, 48)) / 2;
        zpSDK.drawText(markX, 10, mark, 4, 0, 0, false, false);
        zpSDK.drawText(10, 106, "箱号:    ", 2, 0, 0, false, false);
        zpSDK.drawBarCode(80, 70, String.valueOf(packBox.getBoxId()), 128, false, 4, 72);
        int boxIdX = (570 - G.getTextWidth(String.valueOf(packBox.getBoxId()), 16)) / 2;
        zpSDK.drawText(boxIdX, 145, String.valueOf(packBox.getBoxId()), 2, 0, 0, false, false);

      //  zpSDK.drawText(10, 170, packBox.getVendorCompany(), 2, 0, 0, false, false);
        if (totalCount > 1) {
            zpSDK.drawText(240, 170, "共 " + totalCount + " 箱，第 " + index + " 箱", 2, 0, 0, false, false);
        }
        zpSDK.drawText(10, 180 + between, "sku数：" + packBox.getDetails().size() + "条", 2, 0, 0, false, false);
        zpSDK.drawText(175, 180 + between, "件数：" + packBox.getItemNum() + "件", 2, 0, 0, false, false);
        String currentTime = DateUtil.getFormatTimeDate(new Date());
        zpSDK.drawText(345, 180 + between, currentTime, 2, 0, 0, false, false);

        zpSDK.drawText(10, 180 + between * 2, "分拣员：", 2, 0, 0, false, false);
        zpSDK.drawText(240, 180 + between * 2, "订单号：" + packBox.getOrderId(), 2, 0, 0, false, false);

        String nums = UserMessage.getInstance().getUsername();
        zpSDK.drawText(120, 180 + between * 2, nums.substring(nums.indexOf(":") + 1), 2, 0, 0, false, false);

        zpSDK.drawText(10, 200 + between * 2, "--------------------------------------------------", 2, 0, 0, false, false);
        zpSDK.drawText(20, 190 + between * 3, "商品名称", 2, 0, 1, false, false);
        zpSDK.drawText(300, 190 + between * 3, "商品单位", 2, 0, 1, false, false);
        zpSDK.drawText(490, 190 + between * 3, "数量", 2, 0, 1, false, false);

        int k = 0;
        for (int i = 0; i < packBoxDetails.size(); i++) {
            PackBoxDetail detail = packBoxDetails.get(i);
            String leftText = detail.getProductTitle();
            String title = detail.getProductTitle();
            if (title.length() > LEFT_TEXT_MAX_LENGTH) {
                title = title.substring(0, LEFT_TEXT_MAX_LENGTH);
            }
            zpSDK.drawText(20, 200 + between * (k + 4), title, 2, 0, 0, false, false);
            zpSDK.drawText(300, 200 + between * (k + 4), detail.getProductUnit(), 2, 0, 0, false, false);

            int num = detail.getNum();
            String numText = "*" + detail.getNum();
            if (num == 0) {
                numText = "全缺";
                zpSDK.drawText(490, 200 + between * (k + 4), numText, 2, 0, 0, false, false);
            } else {
                zpSDK.drawText(500, 200 + between * (k + 4), numText, 2, 0, 0, false, false);
            }
            //如果标题超过最多的字符就就换行打印
            if (leftText.length() > LEFT_TEXT_MAX_LENGTH) {
                String nextLine = leftText.substring(LEFT_TEXT_MAX_LENGTH, leftText.length());
                zpSDK.drawText(20, 190 + between * (k + 5), nextLine, 2, 0, 0, false, false);
                k++;
            }
            k++;
        }
        zpSDK.print(0, 0);
        zpSDK.disconnect();
        return true;
    }



    public static boolean printPack(PickPrintDataResult pickPrintDataResult) {
        zpBluetoothPrinter zpSDK = new zpBluetoothPrinter(YunmayiApplication.getInstance().getApplicationContext());
        if (YunmayiApplication.bluetoothDevice == null) {
            return false;
        }
        int line = 0;
        List<PickPrintData> pickPrintDataList = pickPrintDataResult.getPickPrintDataList();
        for (int i = 0; i < pickPrintDataList.size(); i++) {
            if (pickPrintDataList.get(i).getName().length() > LEFT_TEXT_MAX_LENGTH) {
                line++;  //如果标题超过最多的字符就就换行打印
            }
            line++;
        }
        int between = 36;
        zpSDK.connect(YunmayiApplication.bluetoothDevice.getAddress());
        zpSDK.pageSetup(570, 200 + between * (line + 4));
        PickOrder pickOrder = pickPrintDataResult.getPickOrder();
        String mark = pickOrder.getAgent_company() + " - " + pickOrder.getMark();
        int markX = (570 - G.getTextWidth(mark, 48)) / 2;
        zpSDK.drawText(markX, 10, mark, 4, 0, 0, false, false);
        zpSDK.drawText(50, 96, "订单号", 2, 0, 0, false, false);
        zpSDK.drawBarCode(140, 70, pickOrder.getOrder_id(), 128, false, 3, 60);
        zpSDK.drawText(10, 146, "拣货员:", 2, 0, 0, false, false);
        zpSDK.drawText(95, 146, pickOrder.getPicker_name().substring(0,3) + "/" + pickOrder.getPicker_mobile(), 2, 0, 0, false, false);
        String currentTime = DateUtil.getFormatTimeDate(new Date());
        zpSDK.drawText(340, 146, currentTime, 2, 0, 0, false, false);
        zpSDK.drawText(10, 146+between, "总价:", 2, 0, 0, false, false);
        zpSDK.drawText(75, 146+between, PriceTransfer.chageMoneyToString(pickPrintDataResult.getOriginTotalPrice()*100), 2, 0, 0, false, false);
        zpSDK.drawText(180, 146+between, "缺货金额:", 2, 0, 0, false, false);
        zpSDK.drawText(300, 146+between, PriceTransfer.chageMoneyToString(pickPrintDataResult.getTotalLessPrice()*100), 2, 0, 0, false, false);
        zpSDK.drawText(380, 146+between, "应收金额:", 2, 0, 0, false, false);
        zpSDK.drawText(500, 146+between, PriceTransfer.chageMoneyToString(pickPrintDataResult.getRealTotalPrice()*100), 2, 0, 0, false, false);
        zpSDK.drawText(10, 160 + between , "_______________________________________________", 2, 0, 0, false, false);
        zpSDK.drawText(90, 165 + between*2, "商品名称", 2, 0, 1, false, false);
        zpSDK.drawText(320, 165 + between*2 , "下单/缺货", 2, 0, 1, false, false);
        zpSDK.drawText(450, 165 + between*2 , "缺货金额", 2, 0, 1, false, false);
        int k = 0;
        for (int i = 0; i < pickPrintDataList.size(); i++) {
            PickPrintData pickPrintData = pickPrintDataList.get(i);
            String leftText = pickPrintData.getName();
            String title = pickPrintData.getName();
            if (title.length() > LEFT_TEXT_MAX_LENGTH) {
                title = title.substring(0, LEFT_TEXT_MAX_LENGTH);
            }
            zpSDK.drawText(10, 170 + between * (k + 3), title, 2, 0, 0, false, false);
            zpSDK.drawText(340, 170 + between * (k + 3), pickPrintData.getOrderNum() + "/" +( pickPrintData.getOrderNum()-pickPrintData.getPickNum()), 2, 0, 0, false, false);
            zpSDK.drawText(480, 170 + between * (k + 3), PriceTransfer.chageMoneyToString(pickPrintData.getLessMoney()*100)+"", 2, 0, 0, false, false);
            //如果标题超过最多的字符就就换行打印
            if (leftText.length() > LEFT_TEXT_MAX_LENGTH) {
                String nextLine = leftText.substring(LEFT_TEXT_MAX_LENGTH, leftText.length());
                zpSDK.drawText(10, 170 + between * (k + 4), nextLine, 2, 0, 0, false, false);
                k++;
            }
            k++;
        }
        zpSDK.print(0, 0);
        zpSDK.disconnect();
        return true;
    }
    public static boolean  drawerPack(PickPrintDataResult pickPrintDataResult){
        zpBluetoothPrinter zpSDK = new zpBluetoothPrinter(YunmayiApplication.getInstance().getApplicationContext());
        if (YunmayiApplication.bluetoothDevice == null) {
            return false;
        }
        int between = 36;
        int line = 0;
        List<PickPrintData> pickPrintDataList = pickPrintDataResult.getPickPrintDataList();
        for (int i = 0; i < pickPrintDataList.size(); i++) {
            if (pickPrintDataList.get(i).getName().length() > LEFT_TEXT_MAX_LENGTH) {
                line++;  //如果标题超过最多的字符就就换行打印
            }
            line++;
        }
        zpSDK.connect(YunmayiApplication.bluetoothDevice.getAddress());
        zpSDK.pageSetup(570, 680 +between*(line+1));
        PickOrder pickOrder =pickPrintDataResult.getPickOrder();
        String mark =pickOrder.getAgent_company() + " - " + pickOrder.getMark();
        int markX = (570 - G.getTextWidth(mark, 48)) / 2;
        zpSDK.drawText(markX, 10, mark, 4, 0, 0, false, false);
        zpSDK.drawText(50, 96, "订单号", 2, 0, 0, false, false);
        zpSDK.drawBarCode(140, 70, pickOrder.getOrder_id(), 128, false, 3, 60);
        int boxIdX = (570 - G.getTextWidth(pickOrder.getOrder_id(), 16)) / 2;
        zpSDK.drawText(boxIdX, 135, pickOrder.getOrder_id(), 2, 0, 0, false, false);
        zpSDK.drawText(10,160,"付款方式：",2, 0, 0, false, false);
        String currentTime = DateUtil.getFormatTimeDate(new Date());
        zpSDK.drawText(380,160,currentTime,2, 0, 0, false, false);
        String payWay = G.getPayWay(pickOrder.getPay_type(),pickOrder.getPay_platform());
        zpSDK.drawText(140,160,payWay,2, 0, 0, false, false);
        zpSDK.drawText(10,196,"联系方式：",2, 0, 0, false, false);
        String info = pickOrder.getBuyer_name()+"/"+pickOrder.getBuyer_mobile();
        zpSDK.drawText(140,196,info,2, 0, 0, false, false);
        zpSDK.drawText(10,232,"店铺名称：",2, 0, 0, false, false);
        String shopInfo = pickOrder.getBuyer_shop_name()+"/"+pickOrder.getBuyer_number();
        zpSDK.drawText(140,232,shopInfo,2, 0, 0, false, false);
        zpSDK.drawText(10,268,"店铺地址：",2, 0, 0, false, false);
        zpSDK.drawText(140,268, pickOrder.getBuyer_address(),2, 0, 0, false, false);
        zpSDK.drawText(10, 304, "总价:", 2, 0, 0, false, false);
        zpSDK.drawText(75, 304, PriceTransfer.chageMoneyToString(pickPrintDataResult.getOriginTotalPrice()*100), 2, 0, 0, false, false);
        zpSDK.drawText(180, 304, "缺货金额:", 2, 0, 0, false, false);
        zpSDK.drawText(300, 304, PriceTransfer.chageMoneyToString(pickPrintDataResult.getTotalLessPrice()*100), 2, 0, 0, false, false);
        zpSDK.drawText(380, 304, "应收金额:", 2, 0, 0, false, false);
        zpSDK.drawText(500, 304, PriceTransfer.chageMoneyToString(pickPrintDataResult.getRealTotalPrice()*100), 2, 0, 0, false, false);
       G.log("xxxxxxxxxx总价"+pickPrintDataResult.getOriginTotalPrice());
        G.log("xxxxxxxxxx缺货金额"+pickPrintDataResult.getTotalLessPrice());
        G.log("xxxxxxxxxx应收金额"+pickPrintDataResult.getRealTotalPrice());
        zpSDK.drawText(10, 340, "_______________________________________________", 2, 0, 0, false, false);
        zpSDK.drawText(90, 376, "商品名称", 2, 0, 1, false, false);
        zpSDK.drawText(320, 376 , "下单/缺货", 2, 0, 1, false, false);
        zpSDK.drawText(450, 376 , "缺货金额", 2, 0, 1, false, false);
        int k = 0;
        for (int i = 0; i < pickPrintDataList.size(); i++) {
            PickPrintData pickPrintData = pickPrintDataList.get(i);
            String leftText = pickPrintData.getName();
            String title = pickPrintData.getName();
            if (title.length() > LEFT_TEXT_MAX_LENGTH) {
                title = title.substring(0, LEFT_TEXT_MAX_LENGTH);
            }
            zpSDK.drawText(10, 376 + between * (k+1), title, 2, 0, 0, false, false);
            zpSDK.drawText(340, 376 + between * (k+1), pickPrintData.getOrderNum() + "/" +( pickPrintData.getOrderNum()-pickPrintData.getPickNum()), 2, 0, 0, false, false);
            zpSDK.drawText(480, 376 + between * (k + 1),  PriceTransfer.chageMoneyToString(pickPrintData.getLessMoney())+"", 2, 0, 0, false, false);
            //如果标题超过最多的字符就就换行打印
            if (leftText.length() > LEFT_TEXT_MAX_LENGTH) {
                String nextLine = leftText.substring(LEFT_TEXT_MAX_LENGTH, leftText.length());
                zpSDK.drawText(10, 376 + between * (k +2), nextLine, 2, 0, 0, false, false);
                k++;
            }
            k++;
        }
        zpSDK.drawText(10, 402 +between * k , "_______________________________________________", 2, 0, 0, false, false);
        zpSDK.drawText(10,438+between * k ,"送 货 员：",2, 0, 0, false, false);
        String sendInfo= pickOrder.getDeliveryman_name()+"/"+pickOrder.getDeliveryman_mobile();
        zpSDK.drawText(140,438+between *k ,sendInfo,2, 0, 0, false, false);
        String saleInfo= pickOrder.getSalesman_name()+"/"+pickOrder.getSalesman_mobile();
        zpSDK.drawText(10,474+between * k ,"业 务 员：",2, 0, 0, false, false);
        zpSDK.drawText(140,474+between * k ,saleInfo,2, 0, 0, false, false);
        zpSDK.drawText(10,510+between *k ,"客服电话：",2, 0, 0, false, false);
        zpSDK.drawText(140,510+between *k ,pickOrder.getService_mobile(),2, 0, 0, false, false);
        String footSet = pickOrder.getFoot_set();
        if (footSet!=null&&footSet.length() > 20) {
            footSet = footSet.substring(0, 20);
            zpSDK.drawText(10,546+between * k ,footSet,2, 0, 0, false, false);
        }
        if (footSet!=null&&footSet.length() > 20) {
            zpSDK.drawText(10,546+between * (k+1) ,pickOrder.getFoot_set().substring(20),2, 0, 0, false, false);
        }
        zpSDK.print(0, 0);
        zpSDK.disconnect();
        return true;
    }
    public static boolean  drawerPack(ReceiptPrintInfo printInfo){
        zpBluetoothPrinter zpSDK = new zpBluetoothPrinter(YunmayiApplication.getInstance().getApplicationContext());
        if (YunmayiApplication.bluetoothDevice == null) {
            return false;
        }
        int between = 36;
        int line = 0;
        List<ReceiptPrintInfo.DetailInfo> pickPrintDataList = printInfo.getDetailList();
        for (int i = 0; i < pickPrintDataList.size(); i++) {
            if (pickPrintDataList.get(i).getProductTitle().length() > LEFT_TEXT_MAX_LENGTH) {
                line++;  //如果标题超过最多的字符就就换行打印
            }
            line++;
        }
        zpSDK.connect(YunmayiApplication.bluetoothDevice.getAddress());
        zpSDK.pageSetup(570, 650 +between*(line+1));
        String mark =printInfo.getAgentCompany() + " - " + printInfo.getMark();
        int markX = (570 - G.getTextWidth(mark, 48)) / 2;
        zpSDK.drawText(markX, 10, mark, 4, 0, 0, false, false);
        zpSDK.drawText(50, 96, "订单号", 2, 0, 0, false, false);
        zpSDK.drawBarCode(140, 70, printInfo.getOrderId(), 128, false, 3, 60);
        int boxIdX = (570 - G.getTextWidth(printInfo.getOrderId(), 16)) / 2;
        zpSDK.drawText(boxIdX, 135, printInfo.getOrderId(), 2, 0, 0, false, false);
        zpSDK.drawText(10,160,"付款方式：",2, 0, 0, false, false);
        String currentTime = DateUtil.getFormatTimeDate(new Date());
        zpSDK.drawText(380,160,currentTime,2, 0, 0, false, false);
        String payWay = G.getPayWay(printInfo.getPayType(),printInfo.getPayPlatform());
        zpSDK.drawText(140,160,payWay,2, 0, 0, false, false);
        zpSDK.drawText(10,196,"联系方式：",2, 0, 0, false, false);
        String info = printInfo.getBuyerName()+"/"+printInfo.getBuyerMobile();
        zpSDK.drawText(140,196,info,2, 0, 0, false, false);
        zpSDK.drawText(10,232,"店铺名称：",2, 0, 0, false, false);
        String shopInfo = printInfo.getBuyerShopName()+"/"+printInfo.getBuyerNumber();
        zpSDK.drawText(140,232,shopInfo,2, 0, 0, false, false);
        zpSDK.drawText(10,268,"店铺地址：",2, 0, 0, false, false);
        zpSDK.drawText(140,268, printInfo.getBuyerAddress(),2, 0, 0, false, false);
        zpSDK.drawText(10, 304, "总价:", 2, 0, 0, false, false);
        zpSDK.drawText(75, 304, String.valueOf(printInfo.getOriginTotalSellPrice()), 2, 0, 0, false, false);
        zpSDK.drawText(180, 304, "缺货金额:", 2, 0, 0, false, false);
        zpSDK.drawText(300, 304,  String.valueOf(printInfo.getTotalOutPrice()), 2, 0, 0, false, false);
        zpSDK.drawText(380, 304, "应收金额:", 2, 0, 0, false, false);
        zpSDK.drawText(500, 304, String.valueOf(printInfo.getRealPayTotalSellPrice()), 2, 0, 0, false, false);
        zpSDK.drawText(10, 340, "_______________________________________________", 2, 0, 0, false, false);
        zpSDK.drawText(90, 376, "商品名称", 2, 0, 1, false, false);
        zpSDK.drawText(320, 376 , "下单/缺货", 2, 0, 1, false, false);
        zpSDK.drawText(450, 376 , "缺货金额", 2, 0, 1, false, false);
        int k = 0;
        for (int i = 0; i < pickPrintDataList.size(); i++) {
            ReceiptPrintInfo.DetailInfo pickPrintData = pickPrintDataList.get(i);
            String leftText = pickPrintData.getProductTitle();
            String title = pickPrintData.getProductTitle();
            if (title.length() > LEFT_TEXT_MAX_LENGTH) {
                title = title.substring(0, LEFT_TEXT_MAX_LENGTH);
            }
            zpSDK.drawText(10, 376 + between * (k+1), title, 2, 0, 0, false, false);
            zpSDK.drawText(340, 376 + between * (k+1), (pickPrintData.getQuantity() + "/" +pickPrintData.getOutOfStockNum()), 2, 0, 0, false, false);
            zpSDK.drawText(480, 376 + between * (k + 1),   String.valueOf(pickPrintData.getOutPrice()), 2, 0, 0, false, false);
            //如果标题超过最多的字符就就换行打印
            if (leftText.length() > LEFT_TEXT_MAX_LENGTH) {
                String nextLine = leftText.substring(LEFT_TEXT_MAX_LENGTH, leftText.length());
                zpSDK.drawText(10, 376 + between * (k +2), nextLine, 2, 0, 0, false, false);
                k++;
            }
            k++;
        }
        zpSDK.drawText(10, 402 +between * k , "_______________________________________________", 2, 0, 0, false, false);
        zpSDK.drawText(10,438+between * k ,"送 货 员：",2, 0, 0, false, false);
        String sendInfo= printInfo.getDeliverymanName()+"/"+printInfo.getDeliverymanMobile();
        zpSDK.drawText(140,438+between *k ,sendInfo,2, 0, 0, false, false);
        String saleInfo= printInfo.getSalesmanName()+"/"+printInfo.getSalesmanMobile();
        zpSDK.drawText(10,474+between * k ,"业 务 员：",2, 0, 0, false, false);
        zpSDK.drawText(140,474+between * k ,saleInfo,2, 0, 0, false, false);
        zpSDK.drawText(10,510+between *k ,"客服电话：",2, 0, 0, false, false);
        zpSDK.drawText(140,510+between *k ,printInfo.getServiceMobile(),2, 0, 0, false, false);
        String footSet = printInfo.getFootSet();
        if (footSet!=null&&footSet.length() > 20) {
            footSet = footSet.substring(0, 20);
            zpSDK.drawText(10,546+between * k ,footSet,2, 0, 0, false, false);
        }
        if (footSet!=null&&footSet.length() > 20) {
            zpSDK.drawText(10,546+between * (k+1) ,printInfo.getFootSet().substring(20),2, 0, 0, false, false);
        }
        zpSDK.print(0, 0);
        zpSDK.disconnect();
        return true;
    }
}
