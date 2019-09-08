package test;

import Bean.Admin;
import Bean.News;
import Dao.DaoAdmin;
import Dao.DaoNews;


import java.util.*;


public class Test {


        public static  void  main(String[] args) throws Exception{

            String name = "张三";
            System.out.println(name.getBytes("utf-8").length);

//            News news = new News("8","行业动态测试标题","养乐多","2018年11月18日3点34分18秒",0,"行业动态","[]","[]");
//
//            DaoNews.insert_news(news);

//            Random rand = new Random();
//
//            //List集合保存
//            List<Integer> list = new ArrayList<Integer>();
//            int num;
//            do{
//                num = rand.nextInt(100);
//                for (Integer item : list) {
//                    if (num==item) {
//                        continue;
//                    }
//                }
//                list.add(num);
//            }while(list.size()<100);
//            for (Integer item : list) {
//                System.out.println(item);
//            }
//
//            Integer[] nums = list.toArray(new Integer[list.size()]);
//
//           System.out.println(length_easy(nums,9,1));

        }


        public static int length_easy(Integer[] s,Integer x, int count){

            Integer temp=s[x];

            if (temp==x) return count;
            else return length_easy(s,temp,count+1);
        }

    }
