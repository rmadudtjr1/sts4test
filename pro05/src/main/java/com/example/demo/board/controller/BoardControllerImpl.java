package com.example.demo.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.service.BoardService;
import com.example.demo.common.file.dto.FileDTO;
import com.example.demo.common.file.repository.FileRepository;

@Controller
@RequestMapping("/board")
public class BoardControllerImpl implements BoardController {
	@Autowired
	BoardService boardService;
	@Autowired
	FileRepository fileRepository;
	
	private static final String FILE_REPO = "/home/ubuntu/app/pro05/file_repo";
	
	@Override
	@GetMapping("/boardList.do")
	public String boardList(Model model) {
		List<BoardDTO> boardList = boardService.boardList();
		
		model.addAttribute("boardList", boardList);
		return "board/boardList";
	}

	@Override
	@GetMapping("/insertBoard.do")
	public String insertBoard(Model model) {
		// TODO Auto-generated method stub
		return "board/insertBoard";
	}

	@Override
	@PostMapping("/insertBoard.do")
	public String insertBoard(BoardDTO dto, List<MultipartFile> files, Model model) throws Exception {
	    boardService.insertBoard(dto);	
	    
	    for (MultipartFile file : files) {
	        if (!file.isEmpty()) {
	            String originalFilename = file.getOriginalFilename();
	            String saveDir = FILE_REPO + "\\" + dto.getArticleNo();
	            		
	            
	            File dir = new File(saveDir);
	            if (!dir.exists()) {
	                dir.mkdirs(); // 폴더가 없으면 생성 (상위 폴더 포함)
	            }
	            
	            Path savePath = Paths.get(saveDir , originalFilename);
	            file.transferTo(savePath.toFile());

	            FileDTO fileDTO = new FileDTO();
	            fileDTO.setArticleNo(dto.getArticleNo());
	            fileDTO.setFileName(originalFilename);
	            
	            fileRepository.save(fileDTO);
	        }
	    }
	    
	    model.addAttribute("message", "게시글이 등록되었습니다.");
	    model.addAttribute("redirectUrl", "/board/boardList.do");
	    
	    return "common/alert";
	}

	@Override
	@GetMapping("/getBoard")
	public String getBoard(@RequestParam("articleNo") int articleNo, Model model) {
		// TODO Auto-generated method stub
		BoardDTO board = boardService.getBoard(articleNo);
		List<FileDTO> fileList = fileRepository.findByArticleNo(articleNo);
		model.addAttribute("board", board);
		model.addAttribute("fileList", fileList);
		System.out.println(board);
		return "board/getBoard.html";
	}

	@Override
	@PostMapping("/getBoard")
	public String getBoard(BoardDTO dto, Model model) {
		// TODO Auto-generated method stub
		boardService.updateBoard(dto);
		model.addAttribute("message", "게시글이 수정되었습니다.");
	    model.addAttribute("redirectUrl", "/board/getBoard?articleNo="+dto.getArticleNo());
	    return "common/alert";
		
	}

	@Override
	@GetMapping("deleteBoard")
	public String deleteBoard(int articleNo, Model model) {
		// TODO Auto-generated method stub
		int result = boardService.deleteBoard(articleNo);
		
		if (result >= 1) {
			model.addAttribute("message", "게시글이 삭제 되었습니다.");
		    model.addAttribute("redirectUrl", "/board/boardList.do");
		} else {
			model.addAttribute("message", "게시글이 삭제에 실패했습니다. 다시 시도하세요.");
		    model.addAttribute("redirectUrl", "/board/getBoard?articleNo="+articleNo);
		}
		return "common/alert";
	}

}
