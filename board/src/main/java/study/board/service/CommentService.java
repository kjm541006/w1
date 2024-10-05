package study.board.service;

import study.board.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    public Comment createComment(Comment comment);
    public List<Comment> getAllComments();
    public Optional<Comment> getCommentById(Long id);
    public Comment updateComment(Long id, Comment updateParams);
    public void deleteComment(Long id);
}
