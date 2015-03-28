package com.propmgr.resource;

import java.util.List;

public class EnquiryResource {
	private long id;
	private long customerId;
	private String customerDisplayName;
	private long userId;
	private String userDisplayName;
	private CodeTableResource source;
	private String originalenquirydate;
	private CodeTableResource budget;
	private CodeTableResource interest;
	private CodeTableResource probability;
	private CodeTableResource currenthomestatus;
	private String latestComment;
	private String followupDate;
	private List<EnquiryCommentResource> enquiryComments;
	
	public EnquiryResource(long id, long customerId,
			String customerDisplayName, long userId, String userDisplayName,
			CodeTableResource source, String originalenquirydate,
			CodeTableResource budget, CodeTableResource interest,
			CodeTableResource probability, CodeTableResource currenthomestatus,
			String latestComment, String followupDate, List<EnquiryCommentResource> enquiryComments) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.customerDisplayName = customerDisplayName;
		this.userId = userId;
		this.userDisplayName = userDisplayName;
		this.source = source;
		this.originalenquirydate = originalenquirydate;
		this.budget = budget;
		this.interest = interest;
		this.probability = probability;
		this.currenthomestatus = currenthomestatus;
		this.latestComment = latestComment;
		this.followupDate = followupDate;
		this.enquiryComments = enquiryComments;
	}

	public long getId() {
		return id;
	}

	public long getCustomerId() {
		return customerId;
	}

	public String getCustomerDisplayName() {
		return customerDisplayName;
	}

	public long getUserId() {
		return userId;
	}

	public String getUserDisplayName() {
		return userDisplayName;
	}

	public CodeTableResource getSource() {
		return source;
	}

	public String getOriginalenquirydate() {
		return originalenquirydate;
	}

	public CodeTableResource getBudget() {
		return budget;
	}

	public CodeTableResource getInterest() {
		return interest;
	}

	public CodeTableResource getProbability() {
		return probability;
	}

	public CodeTableResource getCurrenthomestatus() {
		return currenthomestatus;
	}

	public String getLatestComment() {
		return latestComment;
	}

	public String getFollowupDate() {
		return followupDate;
	}

	public List<EnquiryCommentResource> getEnquiryComments() {
		return enquiryComments;
	}
}
