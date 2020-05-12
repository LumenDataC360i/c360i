package com.nissan.extractors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.allsight.entity.impl.Entity;
import com.allsight.indexes.extractor.util.AbstractSearchIndexExtractor;

public class HBaseExtractor extends AbstractSearchIndexExtractor {
	@Override
	public List<String> extract(Entity<?> entity) throws IOException {
    List<String> arrayList = new ArrayList<>();
    arrayList.add(entity.toString());
    return arrayList;
  }
}