# Road-to-android
wrap_content 表示当前元素的高度只要能刚好包含里面的内容
requestWindowFeature(window.feature_no_title);隐藏标题栏 但是要写在setContentView()之前
findViewById()方法获取布局文件中定义的元素
Intent intent =new Intent(Intent.ACTION_VIEW);
intent.setData(Uri.parse());
  action为intent.ACTION_VIEW 这是一个安卓系统内置动作
          intent.ACTION_DIAL 拨号内置动作（data部分要指定协议为tel）
  Uri.parse()将网址解析成uri对象 再调用setdata()将uri对象传递过去
  day 2016/7/24
