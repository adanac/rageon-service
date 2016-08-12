package com.adanac.tool.rageon.service.file;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.adanac.framework.web.controller.BaseResult;
import com.adanac.tool.rageon.file.craw.CrawService;

@Service
public class CrawServiceImpl implements CrawService {

	@Override
	public BaseResult proCraw1(String url, String element) {
		BaseResult br = new BaseResult();
		StringBuilder sb = new StringBuilder();
		try {
			Document doc = Jsoup.connect(url).timeout(0).get();
			Elements items = doc.select(element);
			for (Element item : items) {
				Elements links = item.select("a");
				for (Element link : links) {
					link.attr("href", link.attr("abs:href"));
				}

				Elements imgs = item.select("img");
				for (Element img : imgs) {
					img.attr("src", img.attr("abs:src"));
				}
				sb.append(item.html().toString());
			}
			br.setContent(sb);
			br.setStatus("pro success");

		} catch (Exception e) {
			br = new BaseResult();
			br.setErrorMsg("操作失敗");
		}
		return br;
	}

}
