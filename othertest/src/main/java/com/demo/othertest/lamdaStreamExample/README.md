# StreamExample
 java8 stream的一些简单应用

      /***
         * 无限流
         * 产生随机数
         * */

        String str1=Stream.generate(()->Math.random()).limit(5).map(Objects::toString).collect(Collectors.joining(","));
/**
 * 字符串 拼接
 * */
        String str2=Stream.iterate(0,i->i+1).limit(5).map(Objects::toString).collect(Collectors.joining(","));
/**
 * group by 之后利用reducing去重复
 * */
        1.Map<Object, Map> collect=maps.stream().collect(Collectors.groupingBy(p->p.get("did"),
                Collectors.collectingAndThen(Collectors.reducing((c1,c2)->((Timestamp)c1.get("ect")).getTime()>((Timestamp)c2.get("ect")).getTime()?c1:c2),
                        Optional::get)));
        return collect;

        2.Map<String, A> collect=list.stream().collect(Collectors.groupingBy(A::getName,Collectors.collectingAndThen(Collectors.reducing((c1,c2)->c1.getI()>c2.getI()?c1:c2),
                Optional::get)));
/**
 * map to list
 * */
        List<A> list=new ArrayList<>(collect.values());
/**
 * group by分组聚合
 * */
        HashMap<String, List<S>>collect=collect1.stream().collect(Collectors.groupingBy(S::getPid,HashMap::new,Collectors.toList()));

        Map<String, List<Map>>map=sysRoles.stream().collect(Collectors.groupingBy(SysRole::getFz,Collectors.mapping(m->new LinkedHashMap(){{
            put("id",m.getId());
            put("rolename",m.getRolename());
            put("systemtype",m.getSystemtype());
        }},toList())));自定义

/**
 * peek:可以看到流中的数据经过每个处理点时的状态
 * peek为中间操作，需要一个终止操作
 * */
        Arrays.stream(ss).peek(System.out::println).collect(Collectors.toList());
        Arrays.asList(ss).stream().map(m->m.length()).peek(System.out::println);
