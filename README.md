# clip-image

仿微信图片裁剪。

仿微信的裁剪控件，拖动图片而裁剪框不动。可设置长宽比例，裁剪边框的边距，遮罩层颜色，提示文字及文字大小等。

##使用方式

**XML代码**

```xml
    <com.githang.clipimage.ClipImageView
        android:id="@+id/clip_image_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_above="@+id/bottom"
        app:civClipPadding="@dimen/padding_common"
        app:civHeight="2"
        app:civMaskColor="@color/viewfinder_mask"
        app:civWidth="3"/>
```

**Java代码**

设置图片：

```java
    mClipImageView.setImageBitmap(target);
```

裁剪图片：

```java
    return mClipImageView.clip();
```

##大图裁剪
请参考app模块里的ClipImageActivity。

##目前支持属性

- `civHeight` 高度比例，默认为1
- `civWidth` 宽度比例，默认为1
- `civWidth` 裁剪的提示文字
- `civTipTextSize` 裁剪的提示文字的大小
- `civMaskColor` 遮罩层颜色
- `civClipPadding` 裁剪框边距
