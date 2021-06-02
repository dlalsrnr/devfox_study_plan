package na.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import na.spring.domain.BoardAttachVO;
import na.spring.domain.BoardVO;
import na.spring.domain.Criteria;
import na.spring.mapper.BoardAttachMapper;
import na.spring.mapper.BoardMapper;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardMapper mapper;

    @Autowired
    private BoardAttachMapper attachMapper;

    @Transactional
    @Override
    public void register(BoardVO board) {
        log.info("register.........." + board);
        mapper.insertSelectKey(board);
        if (board.getAttachList() == null || board.getAttachList().size() <= 0)
            return;
        board.getAttachList().forEach(attach -> {
            attach.setBno(board.getBno());
            attach.setFileType((attach.getFileType().equals("true") ? "1" : "0"));
            attachMapper.insert(attach);
        });
    }

    @Override
    public BoardVO get(Long bno) {
        log.info("get............" + bno);
        return mapper.read(bno);
    }

    @Transactional
    @Override
    public boolean modify(BoardVO board) {
        log.info("modify............" + board);
        attachMapper.deleteAll(board.getBno());
        boolean modifyReSult = mapper.update(board) == 1;
        if (modifyReSult && board.getAttachList() != null && board.getAttachList().size() > 0) {
            board.getAttachList().forEach(attach -> {
                attach.setBno(board.getBno());
                attach.setFileType((attach.getFileType().equals("true") ? "1" : "0"));
                attachMapper.insert(attach);
            });
        }
        return modifyReSult;
    }

    @Transactional
    @Override
    public boolean remove(Long bno) {
        log.info("remove............." + bno);
        attachMapper.deleteAll(bno);
        return mapper.delete(bno) == 1;
    }

    @Override
    public List<BoardVO> getList(Criteria cri) {
        log.info("get List with criteria....................." + cri);
        return mapper.getListWithPaging(cri);
    }

    @Override
    public int getTotal(Criteria cri) {
        log.info("get total count");
        return mapper.getTotalCount(cri);
    }

    @Override
    public List<BoardAttachVO> getAttachList(Long bno) {
        log.info("get Attach list by bno" + bno);
        return attachMapper.findByBno(bno);
    }
}
