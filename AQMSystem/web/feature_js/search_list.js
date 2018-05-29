const clear_ul = {
    listStyle: 'none',
    outline: 'none',
};

class CacheList extends React.Component {
    constructor(props) {
        super(props);
        this.handleSelectItem = this.handleSelectItem.bind(this);
        this.handleScroll = this.handleScroll.bind(this);
        this.expandList = this.expandList.bind(this);
        this.expandList = this.expandList.bind(this);
    }

    handleSelectItem(e) {
        this.props.onSelect(e.target.innerHTML);
    }

    expandList() {
        let ele = document.getElementById("_sub_list" + this.props.in_id);
        let s_list = this.props.s_list;
        let i = s_list.length >= 10 ? 10 : s_list.length;
        for (; i > 0; i--) {
            let li = document.createElement('li');
            let a = document.createElement('a');
            a.innerHTML = s_list.shift();
            li.appendChild(a);
            li.style.width = '300px';
            a.onclick = this.handleSelectItem;
            ele.appendChild(li);
        }

    }

    handleScroll(e) {
        let nDivHeight = e.target.clientHeight;
        let nScrollHeight = e.target.scrollHeight;
        let nScrollTop = e.target.scrollTop;
        if (nScrollTop + nDivHeight >= nScrollHeight) {
            clearTimeout(this.props.timeout);
            this.props.timeout = setTimeout(this.expandList, 50);
        }
    }

    render() {
        let s_list = this.props.s_list;
        let i = s_list.length >= 10 ? 10 : s_list.length;
        let children = []
        for (; i > 0; i--) {
            children.push(<li><a onClick={this.handleSelectItem}>{s_list.shift()}</a></li>)
        }

        return (
            <div id={"_sub_list_root" + this.props.in_id} className='sub_list' onScroll={this.handleScroll}>
                <ul id={"_sub_list" + this.props.in_id} style={clear_ul}>{children}</ul>
            </div>
        )
    }

}

class SearchList extends React.Component {

    constructor(props) {
        super(props);
        let ar = this.props.s_list.slice();
        this.handleChange = this.handleChange.bind(this);
        this.handleLeave = this.handleLeave.bind(this);
        this.handleIn = this.handleIn.bind(this);
        this.handleItemSelect = this.handleItemSelect.bind(this);
        this.state = {
            s_list: ar,
            in_key: this.props.init_key ? this.props.init_key : '',
            new_search: true,
        };

    }

    handleItemSelect(value) {
        this.setState({
            new_search: false,
            in_key: value,
        });
        document.getElementById('_sub_list_root' + this.props.in_id).style['visibility'] = 'hidden';
    }

    handleChange(e) {
        if (!this.state.new_search) {
            this.setState({
                new_search: true,
            });
            return;
        }
        document.getElementById('_sub_list_root' + this.props.in_id).style['visibility'] = 'visible';
        let in_key = e.target.value;
        let n_list = [];
        let s_list = this.props.s_list;
        for (let i in s_list) {
            if (s_list[i].indexOf(in_key) >= 0) {
                n_list.push(s_list[i]);
            }
        }
        this.setState({
            s_list: n_list,
            in_key: e.target.value,
            new_search: true,
        })
    };

    handleLeave() {
        document.getElementById('_sub_list_root' + this.props.in_id).style['visibility'] = 'hidden';
        document.getElementById('date_choose').style['visibility'] = 'visible';
    }

    handleIn() {
        document.getElementById('_sub_list_root' + this.props.in_id).style['visibility'] = 'visible';

    }

    render() {
        return (
            <div>
                <input id={this.props.in_id} className="_sub_list_search" type="text" value={this.state.in_key}
                       onChange={this.handleChange} onFocus={this.handleIn} onBlur={this.handleLeave}/>
                <CacheList in_id={this.props.in_id} onSelect={this.handleItemSelect} s_list={this.state.s_list}/>
            </div>
        )

    }

}