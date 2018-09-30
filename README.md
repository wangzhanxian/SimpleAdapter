# SimpleAdapter
一个极其简单的adapter，不管你的列表有多少类型，多么复杂，adapter无需增加一行代码，从此告别臃肿重复的adapter和ViewHolder代码。  
  
# 使用  
  
  Add it in your root build.gradle at the end of repositories:  
	allprojects {  
		repositories {  
			...  
			maven { url 'https://jitpack.io' }  
		}  
	}  
   Add the dependency  
	dependencies {  
	        implementation 'com.github.wangzhanxian:SimpleAdapter:1.0.0'    
	}  
  

为了更加方便的操作，添加了注解@LayoutId进行配置布局文件Id，但是在Library中的R文件生成的Id不在被final修饰，所以如果在Library使用，可以通过@LayoutResName注解配置资源文件名字。  
  
并且依然流畅的支持多类型，不管你有多少个类型，只需要通过registCell(@NonNull ICell cell)方法进行注册即可，如果你想添加hear或者footer，你可以通过
registSpecialCell(@NonNull ICell cell)方法进行注册。  
如果你需要加载更多的功能，可以直接调用registLoadMoreHelper(LoadMoreHelper loadMoreHelper)方法。如果你想提前加载更多，你可以调用registLoadMoreHelper(int preLoadCount, LoadMoreHelper loadMoreHelper)方法。 加载结束后你可以调用loadMoreEnd(List<T> datas)，自动更新加载状态；当然需要满足相应的规则。加载更多提供了默认的实现类DefaultLoadMoreImpl，当然你可以高度定制自己的布局，状态等，可以继承BaseLoadMoreCell类定制自己的加载更多；  
    
如果你需要添加item的点击事件或者长按事件，可以调用setOnItemClickListener(OnItemClickListener listener, @IdRes int... ids)和setOnItemLongClickListener(OnItemLongClickListener listener, @IdRes int... ids)方法，有时候我们需要对item的某一个View添加事件，所以，你可以把
view的Id传入，将会自动添加监听事件，但是请注意每一个cell中的的id最好不要重复，不然都会被监听；
那最后的写法到底有多流畅呢，看下面：

rv_list.setAdapter(mSmartAdapter = new SmartAdapter<>(this, getList())  
                .registCell(new TypeOneCell())  
                .registCell(new TypeTwoCell())  
                .registLoadMoreHelper(new SmartAdapter.LoadMoreHelper() {  
                    @Override  
                    public BaseLoadMoreCell getLoadMoreCell() {  
                        return new DefaultLoadMoreImpl(DefaultLoadMoreImpl.STATUS_DEFAULT);  
                    }  
 
                    @Override
                    public void requestLoadMore() {  
                        new Handler().postDelayed(new Runnable() {  
                            @Override  
                            public void run() {  
                                mSmartAdapter.loadMoreEnd(getList());  
                            }  
                        },3000);  
                    }  
                })  
                .setOnItemClickListener(new SmartAdapter.OnItemClickListener() {  
                    @Override  
                    public void onClick(View view, ViewHolder holder, int position) {  
  
                    }  
                })  
                .setOnItemLongClickListener(new SmartAdapter.OnItemLongClickListener() {  
                    @Override  
                    public boolean onLongClick(View view, ViewHolder holder, int position) {  
                        return false;  
                    }  
                }));   
                      
    一直点下去就可以了，喜欢的，欢迎star。  
