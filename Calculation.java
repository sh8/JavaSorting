
public class Calculation {
  PersonalResult[] personalArray;   // 組に所属する学生のためのオブジェクト
                             // PersonalResultの配列を格納する．
  InputFile inResult;        // InputFileのオブジェクトを格納する．
  Histgram  totalPointHist;  // 最終成績の点数のヒストグラムのオブジェクト
  Histgram  reportSumHist;   // レポート点数和のヒストグラムのオブジェクト
  Histgram  midTermExamHist; // 中間試験の点数のヒストグラムのオブジェクト
  Histgram  termExamHist;    // 期末試験の点数のヒストグラムのオブジェクト
  Histgram  randHist;        // ランダムな値のヒストグラムのオブジェクト

  // コンストラクター。ファイル名を入力してそのデータを読み込む
  Calculation(String fileName) {
    inResult        = new InputFile(); // 読み込むためのオブジェクトと作成
    // データの読み込みと，評価値の計算
    personalArray     = inResult.readClass(fileName);
    // ヒストグラムのオブジェクトの作成
    totalPointHist  = new Histgram(10, 120);
    reportSumHist   = new Histgram(10, 250);
    midTermExamHist = new Histgram(10, 100);
    termExamHist    = new Histgram(10, 100);
    randHist        = new Histgram(100000, 100000000);
    // ヒストグラムのオブジェクトに点数を登録。
    for (int loop = 0 ; loop < personalArray.length ; ++loop) {
      totalPointHist.addPoint(personalArray[loop].totalPoint);
      reportSumHist.addPoint(personalArray[loop].reportSum);
      midTermExamHist.addPoint(personalArray[loop].midTermExam);
      termExamHist.addPoint(personalArray[loop].termExam);
      randHist.addPoint(personalArray[loop].rand);
    }
  }

  // 組の結果を表示する．学籍番号，氏名，レポートの点数，中間試験の点数，
  // 期末試験の点数，総合成績の点数，乱数を表示する．
  void printResult() {
    if (personalArray == null) return;
    for(int loop = 0 ; loop < personalArray.length ; ++loop) {
      personalArray[loop].printResult();
    }
  }

  // 各種点数のヒストグラム(ある点数の範囲内の人数)を表示する
  void printHist(int type) {
    switch(type) {
    case 0 :
      totalPointHist.printHist(); break;
    case 1 :
      reportSumHist.printHist(); break;
    case 2 :
      midTermExamHist.printHist(); break;
    case 3 :
      termExamHist.printHist(); break;
    case 4 :
      randHist.printHist(); break;
    }
  }

  // 最大値をもつ学生のデータを表示する
  void printMax(int type) {
    if (personalArray == null) { // データが存在しないときの処理
      System.out.println("データがありません");
      return;
    }
    int maxIndex = 0; // 最大値をもつpersonalArray[]のインデックス

    // maxIndexを求める。
    for (int loop = 1 ; loop < personalArray.length ; ++loop) {
      if ( PersonalResult.gt(personalArray[loop], personalArray[maxIndex], type ) ) {
	maxIndex = loop;
       }
    }

    // personalArray[maxIndex]と同じ値を持つものを表示する。
    for (int loop = 1 ; loop < personalArray.length ; ++loop) {
      if (PersonalResult.eq(personalArray[loop], personalArray[maxIndex], type)) {
	 personalArray[loop].printResult();
      }
    }
  }

  // 選択ソートを行う．
  // 0が総合成績，1がレポート，2が中間試験，3が期末試験，4がランダムな値
  // で判断する．
  void sortSelect(int type) {
    if (personalArray == null) { // データが存在しないときの処理
      System.out.println("データがありません");
      return;
    }

    for(int loop = 0 ; loop < personalArray.length - 1 ; ++loop) {
      // 現在の探索域の中で最小値をもつpersonalArray[]のインデックスを格納する
      int minIndex = loop;

      // minIndexを求める。
      for(int search = loop + 1 ; search < personalArray.length ; ++search) {
	if (PersonalResult.gt(personalArray[minIndex], personalArray[search], type)) {
	  minIndex = search;
	}
      }
      // personalArray[minIndex]とpersonalArray[loop]を入れ替える。
      PersonalResult minPerson = personalArray[minIndex];
      personalArray[minIndex]  = personalArray[loop];
      personalArray[loop] = minPerson;
    }
  }

  // クイックソートを行うメソッド
  void sortQuick(int type) {
    if (personalArray == null) { // データが存在しないときの処理
      System.out.println("データがありません");
      return;
    }
    sortQuickPartial(0, personalArray.length - 1, type);
  }

  // クイックソートを行うための作業用のメソッド．
  // personalArray[start]からpersonalArray[end]まで，クイックソートを行う．
  void sortQuickPartial(int start, int end, int type) {
    PersonalResult pivot;  // ピボットとするデータ
    PersonalResult next;   // ピボットとの大小関係を調べ，
    int endSmall;      // ピボット以下のものを次に格納する場所を示すインデックス
    int startLarge;    // ピボット以上のものを次に格納する場所を示すインデックス
    boolean eq;            // nextとpivotが等しい場合にtrueとする
    boolean eqSep = false; // nextとpivotが等しい場合に，nextが大きいとして
                           // 処理をする。

    // startがend以上ならば戻る。
    if(start >= end) return;

    // pivotは，配列の処理する部分の先頭
    // nextの初期値をpivotに決める。
    next = pivot = personalArray[start];
    // pivotより以上・以下の場合の格納する初期位置設定する。
    endSmall = start;
    startLarge = end;

    // 配列をpivot以上，以下に分けていく。
    for(int loop = 0 ; loop <= end - start ; loop++) {
      // nextとpivotが等しい場合は，eqをtrueとし，eqSepの論理値を反転する。
      if (eq = PersonalResult.eq(next, pivot, type)) eqSep = !eqSep;
      if ((eq && eqSep) || PersonalResult.gt(next, pivot, type) ) {
	// nextがpivotより大きいか，等しくてeqSepがtrueのときの処理
	PersonalResult swap = personalArray[startLarge];
	personalArray[startLarge--] = next;
	next = swap;
      } else {
	// nextがpivotより小さいか，等しくてeqSepがfalseのときの処理
	personalArray[endSmall++] = next;
	next = personalArray[endSmall];
      }
    }
    // 分割された両者に対して，sortQuickPartial()を呼び出す。
    sortQuickPartial(start, endSmall - 1, type);
    sortQuickPartial(endSmall, end, type);
  }

  // マージソートを行う。
  void sortMerge(int type) {
    int nData = personalArray.length;
    int length, start, nData1, nData2, i;
    int rest_number = 0;
    PersonalResult[] work = new PersonalResult[nData];
    PersonalResult[] in, out, swap;

    in = personalArray;
    out = work;
    length = 1;
    while(length < nData)  {
      start = 0;
      while(start < nData)  {
        nData1 = nData2 = length;
        // nData1とnData2を計算する。
        if(nData%(2*length) != 0 && start + 2*length >= nData){
          if(rest_number == 0){
            rest_number = nData%(2*length);
          }else{
            if((nData/nData1)%2 == 1){
              sortedMerge(start, nData1, rest_number, in, out, type);
              rest_number += length;
            }
          }
        } else {
          sortedMerge(start, nData1, nData2, in, out, type);
        }
	start += length + length;
      }
      length *= 2;
      swap = in; in = out; out = swap; // inとoutを交換する。

      if(length >= nData){
        break;
      }

    }
    // Cpy final result
    for(i = 0 ; i < nData ; i++)  {
      personalArray[i] = in[i];
    }
  }

  // マージソートのためのソートされた2つのデータ
  // (inDataのstartからnData1個，それに引き続くinDataのnData2個) をマージして，
  // outDataのstartからnData1 + nData2個に出力する。
  // nData1, nData2 が，0または負の場合は，そのデータがないものとして動作する。
  void sortedMerge(int start, int nData1, int nData2, PersonalResult[] inData,
		   PersonalResult[] outData, int type) {
    int index1 = start;          // 1番めのデータで，次にデータを取る位置
    int index2 = start + nData1; // 2番めのデータで，次にデータを取る位置
    int index0 = start;          // 出力で次にデータを置く位置
    int n1 = 0, n2 = 0;          // それぞれの入力から取り出したデータ数

    while(n1 < nData1 && n2 < nData2){
      if (PersonalResult.gt(inData[index2], inData[index1], type)) {
          outData[index0++] = inData[index1++];
          ++n1;
      }
      else {
          outData[index0++] = inData[index2++];
          ++n2;
      }
    }

    while(n1 < nData1) {
        outData[index0++] = inData[index1++];
        ++n1;
    }

    while(n2 < nData2){
        outData[index0++] = inData[index2++];
        ++n2;
    }
  }
}
