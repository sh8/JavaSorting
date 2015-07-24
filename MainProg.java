public class MainProg {
  // メインメソッドである．
  // ClassCalcをコンストラクトしてデータを読み込む．
  // 引数で与えられた，命令(comman)を実行する。
  // 0        : 入力データをそのまま出力する。
  // 10       : 総合成績のヒストグラムを表示する。
  // 11       : レポートの成績のヒストグラムを表示する。
  // 12       : 中間試験の成績のヒストグラムを表示する。
  // 13       : 期末試験の成績のヒストグラムを表示する。
  // 13       : ランダムな値のヒストグラムを表示する(0から100000000まで)。
  // 20       : 総合成績の最大値を表示する。
  // 21       : レポートの成績の最大値を表示する。
  // 22       : 中間試験の成績の最大値を表示する。
  // 23       : 期末試験の成績の最大値を表示する。
  // 24       : ランダムな値の最大値を表示する。
  // 30,40,50 : 総合成績をソートして表示する。
  // 31,41,51 : レポートの成績をソートして表示する。
  // 32,42,52 : 中間試験の成績をソートして表示する。
  // 33,43,53 : 期末試験の成績をソートして表示する。
  // 34,44,54 : ランダムな値をソートして表示する。
  // (30番台が選択ソート，40番台がクイックソート，50番台がマージソート)
  // 実行方法：
  //     java MainProg 0 1000.data
  // のように，コマンドとファイル名を指定して実行する。

  public static void main(String[] args) {
    int command = Integer.parseInt(args[0]);
    Calculation cCalc = new Calculation(args[1]);
    switch(command) {
    case 0: 
      cCalc.printResult();
      break;
    case 10:
    case 11:
    case 12:
    case 13:
    case 14:
      cCalc.printHist(command - 10); break;
    case 20:
    case 21:
    case 22:
    case 23:
    case 24:
      cCalc.printMax(command - 20); break;
    case 30:
    case 31:
    case 32:
    case 33:
    case 34:
      cCalc.sortSelect(command - 30); cCalc.printResult(); break;
    case 40:
    case 41:
    case 42:
    case 43:
    case 44:
      cCalc.sortQuick(command - 40); cCalc.printResult(); break;
    case 50:
    case 51:
    case 52:
    case 53:
    case 54:
      cCalc.sortMerge(command - 50); cCalc.printResult(); break;
    }
  }
}
