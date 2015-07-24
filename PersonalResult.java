import java.util.regex.*;

class PersonalResult {

  String stdntNo;     //学籍番号
  String name;        //氏名
  int[]  reportPoint; //レポートの成績の値を格納する配列
  int    totalPoint;  //最終成績の成績の値
  int    reportSum;   //レポートの成績の値の和
  int    midTermExam; //中間試験の成績の値
  int    termExam;    //期末試験の成績の値
  int    rand;        //ランダムデータ(ソート実験用)

  // コンストラクタ．データファイルの１行の解析結果を受け取る．それを元に，
  // レポートの成績の配列の領域を確保し，各種成績の値を計算し，格納する．
  PersonalResult(Matcher mD) {
    stdntNo        = mD.group(1);
    name           = mD.group(2);
    reportPoint    = new int[5];
    reportPoint[0] = reportCalc(mD.group(3),  mD.group(4));
    reportPoint[1] = reportCalc(mD.group(5),  mD.group(6));
    reportPoint[2] = reportCalc(mD.group(7),  mD.group(8));
    reportPoint[3] = reportCalc(mD.group(9),  mD.group(10));
    reportPoint[4] = reportCalc(mD.group(11), mD.group(12));
    midTermExam    = examCalc(mD.group(13));
    termExam       = examCalc(mD.group(14));
    rand           = Integer.parseInt(mD.group(15));
    reportSum      = 0;
    for (int loop = 0 ; loop < 5 ; ++loop) reportSum += reportPoint[loop];
    totalPoint = (midTermExam + termExam + 1) / 2 + (reportSum + 19) / 20;
  }

  // 個人のデータを出力する．学籍番号，氏名，レポートの成績の和，中間試験
  // の点数，期末試験の点数，最終成績を1行で出力する．
  void printResult() {
    System.out.printf("%8s %14s %3d %3d %3d %3d %14d\n", stdntNo, name, 
		      reportSum, midTermExam, termExam, totalPoint, rand);
  }
  
  // in1の成績の方がin2よりも高い場合にtrueを返し，そうでない場合はfalseを
  // 返す．typeは，判断に使う成績を示す．0が総合成績，1がレポートの和，
  // 2が中間試験，3が期末試験，4がランダムな値で判断する．
  static boolean gt(PersonalResult in1, PersonalResult in2, int type) {
    switch(type) {
    case 0:
      if (in1.totalPoint  > in2.totalPoint) return true;
      break;
    case 1:
      if (in1.reportSum   > in2.reportSum) return true;
      break;
    case 2:
      if (in1.midTermExam > in2.midTermExam) return true;
      break;
    case 3:
      if (in1.termExam    > in2.termExam) return true;
      break;
    case 4:
      if (in1.rand        > in2.rand) return true;
      break;
    }
    return false;
  }
  
  // in1の成績とin2が等しい場合にtrueを返し，そうでない場合はfalseを
  static boolean eq(PersonalResult in1, PersonalResult in2, int type) {
    switch(type) {
    case 0:
      if (in1.totalPoint == in2.totalPoint) return true;
      break;
    case 1:
      if (in1.reportSum  == in2.reportSum) return true;
      break;
    case 2:
      if (in1.midTermExam == in2.midTermExam) return true;
      break;
    case 3:
      if (in1.termExam == in2.termExam) return true;
      break;
    case 4:
      if (in1.rand == in2.rand) return true;
      break;
    }
    return false;
  }

  // レポートの成績を計算して，戻り値とする．
  // jiki  : 提出時期
  // point : 点数
  int reportCalc(String jiki, String point) {
    if (jiki.equals("x")) return 0;
    else {
      int jikiN  = Integer.parseInt(jiki);
      int pointN = Integer.parseInt(point);
      switch(jikiN) {
      case 1:
	return pointN * 2 + 10;
      case 2:
	return pointN + 10;
      case 3:
	return 10;
      }
      return 0;
    }
  }

  // 試験の成績を計算して，戻り値とする．
  // point : 点数
  int examCalc(String point) {
    if (point.equals("x")) return 0;
    else return Integer.parseInt(point);
  }
}
