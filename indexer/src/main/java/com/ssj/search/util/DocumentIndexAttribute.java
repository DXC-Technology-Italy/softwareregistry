package com.ssj.search.util;

import java.nio.file.Path;

import org.apache.lucene.index.IndexWriter;

public class DocumentIndexAttribute {

	private IndexWriter writer;
	
	public DocumentIndexAttribute(IndexWriter writer, Path file, long millis) {
		super();
		this.writer = writer;
		this.file = file;
		this.millis = millis;
	}
	private Path file;
	private long millis;
	
	
	/** 
	 * @return IndexWriter
	 */
	public IndexWriter getWriter() {
		return writer;
	}
	
	/** 
	 * @return Path
	 */
	public Path getFile() {
		return file;
	}
	
	/** 
	 * @return long
	 */
	public long getMillis() {
		return millis;
	}	
}
