package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class musicTest {

	public static void main(String[] args) {
		while (true){
			musicTest mt = new musicTest();
			mt.searchMusic();
		}
	}

	public void searchMusic(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入需要搜索的歌名：");
		String context = "";
		try {
			context = br.readLine();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println("正在搜:" + context + "\n");

		String listscat = KuGou.nlists(context);
		System.out.println("lists:\n" + listscat);

		System.out.println("请输入序号：");
		Scanner in = new Scanner(System.in);
		int num = in.nextInt();
		KuGou kugou = new KuGou(context, num);
		System.out.println("Info:\n" + kugou.getsongINFO());
	}

}
