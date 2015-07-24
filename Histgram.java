public class Histgram {
  int numInBin[]; // 各ビンの中の人数を入れる
                  // numInBin[0]が0点以上，step点未満の人数
                  // numInBin[1]がstep点以上，2 * step点未満の人数
                  // numInBin[2]が2 * step点以上，3 * step点未満の人数
  int step;       // 各ビンの点数の幅
  int end;        // とりうる点数の最大値

  // コンストラクタ。0点からend点まで，step点ごとのビンを作成する．
  // 各ビンに含まれる人数を格納する配列numInBinの領域を確保する．
  Histgram(int step, int end) {
    this.step = step;
    this.end  = end;
    numInBin  = new int[end / step + 1];
  }

  // ヒストグラムにpoint点を取得した人を付け加える．
  //  (該当するビンを表すnumInBinの要素に1を加算する．)
  void addPoint(int point) {
    // pointに対応するビン番号を計算する。
    int binN = point/step;
    // ビン番号がオーバーしているようならば，データとしない。
    if (binN >= numInBin.length || binN < 0) return;
      numInBin[binN] += 1;
  }

  //現在のヒストグラムの中の人数を印刷で表示する．
  void printHist() {
    for (int loop = 0 ; loop < numInBin.length ; ++loop) {
      System.out.printf("%4d - %4d : %4d \n", 
         loop * step, loop * step + step - 1, numInBin[loop]);
    }
  }
}
