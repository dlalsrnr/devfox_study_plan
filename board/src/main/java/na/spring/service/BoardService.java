package na.spring.service;

import java.util.List;

import na.spring.domain.BoardAttachVO;
import na.spring.domain.BoardVO;
import na.spring.domain.Criteria;

public interface BoardService {
    /**
     * boardに保存されたデータに基づいて掲示物を登録する
     * 
     * @param board 登録する掲示物の情報
     */
    public void register(BoardVO board);

    /**
     * 掲示物番号がbnoの掲示物を検索する
     * 
     * @param bno 掲示物番号がbnoの掲示物を検索する
     * @return 持込んだ掲示物の内容
     */
    public BoardVO get(Long bno);

    /**
     * boardに保存されたデータに基づいて掲示物を修正する
     * 
     * @param board 修正する掲示物の情報
     * @return 修正成功可否
     */
    public boolean modify(BoardVO board);

    /**
     * 掲示物番号がbnoの掲示物を削除する
     * 
     * @param bno 削除する掲示物の番号
     * @return 削除成功可否
     */
    public boolean remove(Long bno);

    /**
     * criを基に掲示物リストを取得
     * 
     * @param cri ページ情報
     * @return 受信した掲示物のlist
     */
    public List<BoardVO> getList(Criteria cri);

    /**
     * criにある検索データを基に全体の掲示物の数を取得
     * 
     * @param cri ページ情報
     * @return 全体の掲示物の数
     */
    public int getTotal(Criteria cri);

    /**
     * DBに保存された添付ファイルのリストを保存
     * 
     * @param bno 添付ファイルリストを読み込む掲示物番号
     * @return 読み込んだ添付ファイルリスト
     */
    public List<BoardAttachVO> getAttachList(Long bno);
}
