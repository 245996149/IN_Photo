var category;

/**
 * 套餐下拉按钮选择器
 * @param category_id
 * @param category_name
 */
function selectCategory(category_id, category_name) {

    category = category_id;
    $("#category_name").val(category_name);

}

