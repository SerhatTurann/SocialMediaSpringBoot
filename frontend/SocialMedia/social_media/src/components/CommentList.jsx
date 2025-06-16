// CommentList.jsx
import React from "react";

const CommentList = ({ comments }) => {
  return (
    <div className="comment-list">
      {comments.map((comment, index) => (
        <div key={index} className="comment-item">
          <p><strong>{comment.userId || "Anonim"}:</strong> {comment.text}</p>
        </div>
      ))}
    </div>
  );
};

export default CommentList;
