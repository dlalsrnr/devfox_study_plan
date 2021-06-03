package na.spring.service;

import java.util.List;

import na.spring.domain.Criteria;
import na.spring.domain.ReplyPageDTO;
import na.spring.domain.ReplyVO;

public interface ReplyService {
    /**
     * コメント登録
     * 
     * @param vo 登録するコメントの情報
     * @return 登録の可否
     */
    public int register(ReplyVO vo);

    /**
     * コメント番号がrnoのコメントを持ってくる
     * 
     * @param rno コメント番号がrnoのコメントを持ってくる
     * @return 読み込んだコメント
     */
    public ReplyVO get(Long rno);

    /**
     * voをもとにコメントを修正
     * 
     * @param vo 修正する書き込みの情報
     * @return 修正の可否
     */
    public int modify(ReplyVO vo);

    /**
     * コメント番号がrnoのコメントを削除
     * 
     * @param rno コメント番号がrnoのコメントを削除
     * @return 削除の可否
     */
    public int remove(Long rno);

    /**
     * 掲示物番号がbnoのコメントをcriをベースに持ってくる
     * 
     * @param cri ページ情報が入っている
     * @param bno 掲示物番号
     * @return 読み込んだコメントリスト
     */
    public List<ReplyVO> getList(Criteria cri, Long bno);

    /**
     * 掲示物番号がbnoのコメントと全体のコメント数をcriをベースに持ってくる
     * 
     * @param cri ページ情報が入っている
     * @param bno 掲示物番号
     * @return 読み込んだコメントリスト、 全体のコメント数
     */
    public ReplyPageDTO getListPage(Criteria cri, Long bno);
}
