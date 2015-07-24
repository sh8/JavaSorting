import java.io.*;
import java.util.regex.*;

public class InputFile {
  BufferedReader reader = null; // ファイルからデータを読むためのオブジェクト
  Pattern        comment;       // コメント行を解析するための正規言語のオブジェクト
  Pattern        dataLine;      // データ行を解析するための正規言語のオブジェクト

  // コンストラクター．正規言語を解析するオブジェクトをコンストラクする．
  InputFile() {
    comment  = Pattern.compile("^#.*");
    dataLine = Pattern.compile("(\\d\\d_\\d\\d\\d\\d\\d)\\s+(\\S+)\\s+([x0-9]+)\\s+([x0-9]+)\\s+([x0-9]+)\\s+([x0-9]+)\\s+([x0-9]+)\\s+([x0-9]+)\\s+([x0-9]+)\\s+([x0-9]+)\\s+([x0-9]+)\\s+([x0-9]+)\\s+([x0-9]+)\\s+([x0-9]+)\\s+([\\-0-9]+).*");
  }

  // ファイル名を受け取り，そのファイルのデータを解析する．そして，解析結果を
  // PersonalResultの配列に格納して，戻り値とする．配列の領域確保および配列
  // の要素となるPersonalResultのオブジェクトのコンストラクトも行う．
  PersonalResult[] readClass(String fileName) {
    int nData = 0;
    PersonalResult[] personalArray = null;
    try {
      reader = new BufferedReader(new FileReader(fileName));
      String line;
      line = reader.readLine();
      while (line != null) {
	Matcher mC = comment.matcher(line);
	if (! mC.matches()) {
	  Matcher mD = dataLine.matcher(line);
	  if (mD.matches()) {
	    ++nData;
	  } else {
	    System.out.println("Error Line : " + line);
	  }
	}
	line = reader.readLine();
      }
      reader.close();
      reader = new BufferedReader(new FileReader(fileName));
      // System.out.println("nData = " + nData);
      personalArray = new PersonalResult[nData];
      int pp = 0;
      line = reader.readLine();
      while (line != null) {
	Matcher mD = dataLine.matcher(line);
	if (mD.matches()) {
	  personalArray[pp++] = new PersonalResult(mD);
	}
	line = reader.readLine();
      }
    } catch (FileNotFoundException e) {
      System.out.println("Fine not found");
    } catch (IOException e) {
      System.out.println("IO exception");
    } finally {
      try {
	if (reader != null) {
	  reader.close();
	}
      } catch (Exception e) {
	System.out.println("Exception");
      }
    }
    return personalArray;
  }
}
