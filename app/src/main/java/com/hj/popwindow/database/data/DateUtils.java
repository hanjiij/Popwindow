package com.hj.popwindow.database.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.hj.popwindow.R;
import com.hj.popwindow.datatype.AppInfo;
import com.hj.popwindow.datatype.FunctionInfo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/27.
 */
public class DateUtils {

    /**
     * @param context 环境
     * @param index  放在数据库里哪一个功能下的参数
     * @param id   功能id
     */
    public static void setFunction(Context context,int index,int id)
    {
       String[]names=context.getResources().getStringArray(R.array.funcName);
        String name=names[id];
//        int [] icons=context.getResources().getIntArray(R.array.icon);
//        int icon=icons[id];

//        System.out.println(">>>>>>>"+icons.toString());
//        System.out.println("icon-------------->"+icon);
//        FunctionInfo functionInfo=new FunctionInfo(id,name,resToByte(icon));
//        MFunction.setFunc(context,id,name,icon);
          int [] icons=getDrawable();
          int icon=icons[id];

        MFunction.setFunc(context,id+1,name,icon);//被改动过
        MFunction.setCommonFunc(context,index,id+1);
    }

   private static int [] getDrawable()
   {
       int [] icon={R.drawable.ag,R.drawable.ag,R.drawable.ag,
               R.drawable.ag,R.drawable.ag, R.drawable.ag,
               R.drawable.ag,R.drawable.ag,R.drawable.ag,R.drawable.ag,R.drawable.ag};
       return icon;
   }

    /**
     * @param context
     * @return  所有常用功能
     */
    public static List<FunctionInfo> getAllFuns(Context context)
    {
        String[]names=context.getResources().getStringArray(R.array.funcName);
        int [] icons=getDrawable();
        List<FunctionInfo> list=new ArrayList<>();
        for (int i=0;i<11;i++)
        {
            String name=names[i];
            int icon=icons[i];
            FunctionInfo functionInfo=new FunctionInfo(i,name,BytesToDrawable(resToByte(context, icon)));
            list.add(functionInfo);
        }
        return list;
    }

//    public static List<FunctionInfo> getAllFuns(Context context)
//    {
//        Cursor cursor=DBHelper.getInstance(context).getAllFunction();
//        List<FunctionInfo> list=new ArrayList<>();
//        if (cursor.moveToFirst())
//        {
//            while (cursor.moveToNext())
//            {
//                String name=cursor.getString(cursor.getColumnIndex("name"));
//                int id=cursor.getInt(cursor.getColumnIndex("id"));
//                int icon=cursor.getInt(cursor.getColumnIndex("icon"));
//
//            }
//        }
//    }
    /**
     * @param context 环境
     * @return    List<MFunction> list
     */
    public static List<FunctionInfo> getFunctions(Context context)
    {
        List<MFunction> list=MFunction.getFunctions(context);
        List<FunctionInfo> fList= new ArrayList< >();
        for (int i=0;i<list.size();i++)
        {
            int icon=list.get(i).icon;
            System.out.println("icon--------------->"+icon);
            byte[] bytes=resToByte(context, icon);
            System.out.println("bytes--------------->"+bytes.length);
            Drawable drawable=BytesToDrawable(bytes);
            System.out.println("drawable------------->"+drawable);
            int id=list.get(i).id;
            System.out.println("id---------->"+id);
            String name=list.get(i).name;
            System.out.println("name---------->"+name);
            FunctionInfo functionInfo=new FunctionInfo(id,name,drawable);
            fList.add(functionInfo);
            System.out.println("------------->success");
        }
        return fList;
    }

    /**
     * @param c  环境
     * @param index  app位置索引
     * @param appInfo  AppInfo appInfo APP信息（PackageName，AppName，Icon）
     */
    public static void insertApp(Context c, int index, AppInfo appInfo)
    {
        MAPP.setAppInfo(c, index+"", appInfo.getPackageName(),appInfo.getAppName(),DrawableToBytes(appInfo.getIcon()));
    }

    /**
     * @param context 环境
     * @return List<Map<String,Object>> list
     */
    public static List<AppInfo> getApps(Context context)
    {
        List<MAPP> app_list = MAPP.getApps(context);
        List<MAPP> list=MAPP.getApps(context);
        List<AppInfo> newList=new ArrayList<AppInfo>();
        for (int i = 0; i < app_list.size(); i++)
        {
            String name=list.get(i).name;
            String namePackage=list.get(i).packageName;
            Drawable drawable=BytesToDrawable(list.get(i).icon);
            AppInfo appInfo=new AppInfo(name,namePackage,drawable);
            newList.add(appInfo);
        }
        return newList;
//        Bitmap ivBitmap = null;
//        for (int i = 0; i < app_list.size(); i++) {
//            if (i == 0) {
//                ivBitmap = BitmapFactory.decodeByteArray(app_list.get(0).icon, 0,
//                        app_list.get(0).icon.length);
//            }
//        }
//        return ivBitmap;
    }

    /**
     * 更新常用功能
     * @param context 环境
     * @param index  索引
     * @param id  id
     * @return 成功返回true
     */
  public static boolean upDateFunc(Context context,int index,int id)
  {
      try {
          MFunction.setCommonFunc(context,index,id);
      } catch (Exception e) {
          e.printStackTrace();
          return false;
      }
      return true;
  }

    /**   更新本地APP
     * @param context 环境
     * @param index  索引
     * @param appInfo AppInfo
     * @return  成功返回 true
     */
    public static boolean upDateMyApp(Context context,int index,AppInfo appInfo)
    {
        try {
            String name=appInfo.getAppName();
            String packageName=appInfo.getPackageName();
            Drawable drawable=appInfo.getIcon();
            byte[] icon=DrawableToBytes(drawable);
            MAPP.upDateMyApp(context, index + "", packageName, name, icon);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *  删除常用APP方法
     * @param context  环境
     * @param index  删除位置的索引 1~8
     * @return  删除成功，返回true
     */
    public static boolean delMyApp(Context context,int index)
    {
        try {
            List<MAPP> list=MAPP.getApps(context);
            MAPP.delMyApp(context, index, list);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 删除常用功能
     * @param context 环境
     * @param index 索引 1~8
     * @return  成功返回true
     */
    public static boolean delFunction(Context context,int index)
    {
        try {
            List<MFunction> list=MFunction.getFunctions(context);
            MFunction.delFunc(context,index,list);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * @param bm drawable转成字节数组
     * @return  byte[]
     */
    private static byte[] DrawableToBytes(Drawable bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        drawableToBitmap(bm).compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * @param b  字节转换成drawable
     * @return
     */
    private static Drawable BytesToDrawable(byte[] b) {
        if (b.length != 0) {
            return new BitmapDrawable(BitmapFactory.decodeByteArray(b, 0, b.length));
        } else {
            return null;
        }
    }

    /**
     * @param drawable drawable 转化成bitmap
     * @return
     */
    private static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private static byte[] resToByte(Context context,int res){
        byte[] xx = null;
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), res);
        int size = bmp.getWidth() * bmp.getHeight() * 4;
        ByteArrayOutputStream baos =null;
        baos = new ByteArrayOutputStream(size);
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        xx = baos.toByteArray();
        return xx;
    }
}
