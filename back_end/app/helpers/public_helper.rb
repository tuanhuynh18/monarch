module PublicHelper
  def column_div_tag(num_columns = 2, &column_body)
    column_div_tag(num_columns, 1, &column_body)
  end
  def column_div_tag(num_columns = 2, scale_factor = 1, &column_body)
    content_tag(:div, style: "display:inline-block;width:#{scale_factor * 100.0 / num_columns * 0.98}%;", &column_body)
  end
end
