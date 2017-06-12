package cn.inphoto.user.dbentity.page;

/**
 * Created by root on 17-3-31.
 */
public class Page {

    // 当前页，默认为1
    private int currentPage = 1;
    // 最大行数，默认为5
    private int pageSize = 50;

    // sql所需的分业条件，由已知条件计算得到
    // 分页的起点(currentPage - 1) * pageSize
    private int begin;

    // 分页的终点currentPage * pageSize -1
    private int end;

    // 总行数，用来计算总页数
    private int rows;

    // 总页数，用来向页面输出，有rows计算而得
    private int totalPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getBegin() {
        begin = (currentPage - 1) * pageSize;
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        end = currentPage * pageSize + 1;
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
        //this.totalPage = this.getTotalPage();
    }

    public int getTotalPage() {
        totalPage = 0;

        if (rows % pageSize == 0) {
            //可以整除，返回相除结果
            totalPage = rows / pageSize;
        } else {
            //不可以整除，返回相除结果+1
            totalPage = rows / pageSize + 1;
        }
        //System.out.println("totalPage=" + totalPage);
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "Page{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", begin=" + begin +
                ", end=" + end +
                ", rows=" + rows +
                ", totalPage=" + totalPage +
                '}';
    }
}
