import React, { useState, useEffect } from "react";
import CommentForm from "./CommentForm";
import CommentList from "./CommentList";

const Comment = ({ postId, comments }) => {
  const [commentList, setCommentList] = useState(comments || []);

  const handleAddComment = (newComment) => {
    setCommentList((prev) => [...prev, newComment]); // Yeni yorum anında eklensin
  };

  useEffect(() => {
    setCommentList(comments); // comments prop değişirse güncelle
  }, [comments]);

  return (
    <div className="comment">
      <h4>Yorumlar</h4>
      <CommentList comments={commentList} />
      <CommentForm postId={postId} onAddComment={handleAddComment} />
    </div>
  );
};

export default Comment;
