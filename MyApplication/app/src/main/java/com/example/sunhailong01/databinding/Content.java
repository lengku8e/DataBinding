package com.example.sunhailong01.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by sunhailong01 on 17/9/19.
 */

public class Content extends BaseObservable {
        private String title;
        private String subTitle;

        public Content(String title, String subTitle) {
            this.title = title;
            this.subTitle = subTitle;
        }

        @Bindable
        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
            notifyPropertyChanged(com.example.sunhailong01.databinding.BR.subTitle);
        }

        @Bindable public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
            notifyPropertyChanged(com.example.sunhailong01.databinding.BR.title);
        }
}
