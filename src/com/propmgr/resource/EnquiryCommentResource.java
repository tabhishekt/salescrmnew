package com.propmgr.resource;


public class EnquiryCommentResource {
	private long id;
	private String userDisplayName;
	private long enquiryId;
	private String commentText;
	private String commentDate;
	private String followupDate;
	
	public EnquiryCommentResource(long id, String userDisplayName, long enquiryId, 
			String commentText, String commentDate, String followupDate) {
		super();
		this.id = id;
		this.userDisplayName = userDisplayName;
		this.enquiryId = enquiryId;
		this.commentText = commentText;
		this.commentDate = commentDate;
		this.followupDate = followupDate;
	}

	public long getId() {
		return id;
	}

	public String getUserDisplayName() {
		return userDisplayName;
	}

	public long getEnquiryId() {
		return enquiryId;
	}

	public String getCommentText() {
		return commentText;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public String getFollowupDate() {
		return followupDate;
	}
}
