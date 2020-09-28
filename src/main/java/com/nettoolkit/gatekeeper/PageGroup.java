package com.nettoolkit.gatekeeper;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import com.nettoolkit.exception.ParsingException;
import com.nettoolkit.json.JSONException;
import com.nettoolkit.json.JSONObject;
import com.nettoolkit.json.JSONArray;

/**
 * PageGroup is a model for classifying different pages on your site based on URL.
 * @see <a href="https://www.nettoolkit.com/docs/gatekeeper/overview/page-groups">Gatekeeper page group docs</a>
 */
public class PageGroup {
    private UUID mId;
    private String mstrName;
    private String mstrPageVisitCheck;
    private List<String> mlistPages = new ArrayList<>();

    protected PageGroup(JSONObject jsonPageGroup) throws ParsingException {
        String strId = jsonPageGroup.optString("id");
        if (strId != null) {
            try {
                mId = UUID.fromString(strId);
            } catch (Exception e) {
                throw new ParsingException("Unable to parse page group ID", e, strId);
            }
        }
        mstrName = jsonPageGroup.optString("name", null);
        mstrPageVisitCheck = jsonPageGroup.optString("page_visit_check", null);
        List<String> listPages = new ArrayList<>();
        JSONArray jsonPages = jsonPageGroup.optJSONArray("pages");
        if (jsonPages != null) {
            for (int i = 0; i < jsonPages.length(); i++) {
                String strPage = jsonPages.optString(i);
                listPages.add(strPage);
            }
        }
        mlistPages = listPages;
    }

    /**
     * Get page group ID.
     *
     * @return page group ID
     */
    public UUID getId() { return mId; }

    /**
     * Get page group name.
     *
     * @return page group name
     */
    public String getName() { return mstrName; }

    /**
     * Get page visit check setting.
     * Page visit check is used to determine how repeat visits to a single page should be counted.
     * Options include <code>"ANY"</code>, <code>"UNVISITED"</code>, and <code>"UNVISITED_WITHOUT_QUERY"</code>.
     *
     * @return page visit check setting
     */
    public String getPageVisitCheck() { return mstrPageVisitCheck; }

    /**
     * Get list of pages.
     *
     * @return list of pages
     */
    public List<String> getPages() { return mlistPages; }
}

