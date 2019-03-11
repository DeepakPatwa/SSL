package com.stackroute.pie.service;

//import com.stackroute.pie.domain.Diseases;
import com.stackroute.pie.domain.Policy;
import com.stackroute.pie.domain.SearchPDM;
import com.stackroute.pie.repository.SearchRepository;
import com.stackroute.pie.repository.SearchValueRepository;
import com.stackroute.pie.service.SearchService;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import springfox.documentation.annotations.Cacheable;

import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

@Service
@CacheConfig(cacheNames = "Policy")
public class SearchServiceImpl implements SearchService {
    //    private SearchRepository searchRepository;

    @Autowired
    private KafkaTemplate<String, SearchPDM> kafkaTemplate;

    @Autowired
    public SearchServiceImpl(SearchRepository searchRepository, SearchValueRepository searchValueRepository) {
        this.searchRepository = searchRepository;
        this.searchValueRepository = searchValueRepository;
    }

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    SearchValueRepository searchValueRepository;

    SearchPDM searchvalue = new SearchPDM();

    int count;

    @Override
    public List<Policy> getAllPolicies(String value) {
        List<Policy> policy1 = new ArrayList<>();
        List<String> tok = new ArrayList<>();

        List<Policy> pol = searchRepository.findAll();
        for (Policy p : pol) {
            System.out.println("Inside policies :" + p);
            tok = p.getDiseasesCovered();
            System.out.println(p.getDiseasesCovered());
            System.out.println("Asd :" + tok);
            for (int t = 0; t < tok.size(); t++) {
                if (tok.get(t).equals(value)) {
                    System.out.println("NAme :" + p);
                    policy1.add(p);
                }
            }
        }
        return policy1;
    }





    //    @Override
    public Policy savePolicy(Policy policy) {
        Policy policy1 = searchRepository.save(policy);
        return policy1;
    }

    public SearchPDM saveSearch(Policy policies) {

        if (searchValueRepository.existsById(policies.getPolicyName())) {
            searchvalue = searchValueRepository.findBySearchValue(policies.getPolicyName());
            System.out.println("Search!!!! " + searchvalue.getSearchValue());
            count = searchvalue.getCount();
            searchvalue.setSearchValue(searchvalue.getSearchValue());
            searchvalue.setCount(++count);
        } else {
            count = 0;
            searchvalue.setSearchValue(policies.getPolicyName());
            searchvalue.setCount(++count);
        }
        SearchPDM c1 = saveCount(searchvalue);
        System.out.println("Saving into SearchValueRepo:" + c1.getSearchValue());
        System.out.println("Saving into SearchValueRepo:" + c1.getCount());
//        kafkaTemplate.send("searchFrequency",searchvalue);
        return searchvalue;
    }


    public SearchPDM saveCount(SearchPDM searchPDM) {

        SearchPDM count1 = searchValueRepository.save(searchPDM);
        return count1;
    }

    public static String[] stopwords = {".", "a", "as", "able", "about", "above", "according", "across", "actually", "after", "afterwards", "again",
            "against", "age", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an",
            "and", "another", "any", "anybody", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appropriate",
            "are", "arent", "around", "as", "aside", "ask", "associated", "at", "available", "away", "be", "became", "because", "become",
            "becomes", "becoming", "been", "before", "behind", "being", "below", "beside", "besides", "best", "better", "between", "both", "brief", "but", "by", "came", "can", "cant", "cannot", "cause", "causes", "certain", "changes",
            "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course",
            "currently", "described", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "during", "each",
            "eg", "eight", "either", "else", "enough", "entirely", "especially", "even", "ever", "every", "everybody", "everyone", "everything",
            "ex", "exactly", "example", "except", "far", "few", "fifth", "first", "five", "for", "forth",
            "four", "from", "further", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "had", "hadnt", "happens",
            "hardly", "has", "hasnt", "have", "havent", "having", "he", "hello", "help", "hence", "her", "here", "heres", "hers", "herself",
            "hi", "him", "himself", "his", "how", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "indicate", "indicated", "indicates", "inner", "instead", "into", "inward", "is", "isnt", "it", "its", "itself", "just", "keep", "keeps",
            "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "let", "lets", "like", "liked", "likely", "little", "look", "looking",
            "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely",
            "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "nor", "normally", "not",
            "nothing", "now", "of", "off", "often", "ok", "okay", "old", "on", "once", "one", "ones", "only", "or", "other", "others", "otherwise",
            "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "probably", "provides", "quite", "rather", "rd", "rs", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same",
            "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sent", "serious", "seriously", "seven",
            "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon",
            "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the",
            "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre",
            "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried",
            "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually",
            "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever",
            "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom",
            "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero",
            "policy", "disease", "diseases", "policies"};


    public static Set<String> stopWordSet = new HashSet<String>(Arrays.asList(stopwords));


    @Cacheable(value = "policies")
    public List<Policy> getByDisease(String disease) {
        return searchRepository.findByDiseasesCovered(disease);
    }

//    @Cacheable(value = "diseases")
//    List<Policy> getByDiseasesCovered(String diseaseName) {
//        return searchRepository.getBDiseasesCovered(diseaseName);
//    }

    @Cacheable(value = "Insurer")
    List<Policy> findByInsurerName(String insurerName) {
        return searchRepository.findByInsurerName(insurerName);
    }

    @Cacheable(value = "policies")
    List<Policy> findByPolicyName(String policyName) {
        return searchRepository.findByPolicyName(policyName);
    }
//
//    @Cacheable(value = "SumInsured")
//    List<Policy> findBySumInsured(int num) {
//        return searchRepository.findBySumInsured(num);
//    }

    @Cacheable(value = "findAll")
    List<Policy> findAll() {
        return searchRepository.findAll();
    }

    public static String[] policyArr ={"Jeevan Sathi", "JeevanAnand" ,"JeevanVima" ,"HealthSecure" ,"JeevanHealth" , "Jeevan" , "ApolloHealthSecure","ApolloHealthCare"};

    public static Set<String> policyString = new HashSet<String>(Arrays.asList(policyArr));

    @Cacheable("policies")
    public List<Policy> tokenString(String value) throws Exception {
        List<String> tokenList = new ArrayList<>();
        List<Policy> policies = new ArrayList<>();
        List<Policy> policies1 = new ArrayList<>();
        List<Policy> policies3= new ArrayList<>();
        SearchPDM searchvalue2 = new SearchPDM();
        int count;
        List<String> newpolicy1 = new ArrayList<>();
        Policy newpolicy;
        String companyString = "MaxBupa,StarHealth,Apollo,Religare";
        //String policyString = "JeevanSathi , JeevanAnand ,JeevanVima ,HealthSecure ,JeevanHealth";
        String diseaseString = " cancer, diabetes, aids, dengue, malaria, tuberculosis, cardiac, heartattack, surgery";

        String result = "";
        String[] words = value.split("\\s+");
        for (String word : words) {
            if (word.isEmpty()) continue;
            if (stopWordSet.contains(word)) continue; //remove stopwords
            result += (word + " ");
        }

        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        InputStream inputStream = getClass().getResourceAsStream("/en-pos-maxent.bin");
        POSModel model = new POSModel(inputStream);
        POSTaggerME tagger = new POSTaggerME(model);
        System.out.println("After NameFinderME");
        String[] token1 = tokenizer.tokenize(result);
        String[] tags = tagger.tag(token1);
        for (String t : tags) {
            System.out.println("T :" + t);
        }
        int j = 0;
        POSSample sample = new POSSample(token1, tags);
//        System.out.println(sample.toString());
        String[] strings = sample.toString().split(" ");
//        System.out.println("strings[0] " + strings[0]);
        int len = strings.length;
//        System.out.println(len);
        if (len == 1) {

            if (strings[0].contains("CD")) {
                List<Policy> addPolicy = new ArrayList<>();
                j = Integer.parseInt(strings[0].split("_")[0]);

                if (j > 1000) {
                    policies=searchRepository.findAll();
                    for(Policy p:policies){
//                        System.out.println("Inside loop"+p.getMinSumInsured());
                        if (p.getMaxSumInsured()>j && p.getMinSumInsured()<j)
                            addPolicy.add(p);
                        searchvalue2=saveSearch(p);
                    }
                    return addPolicy;
                }else if (j < 100) {
                    System.out.println("Inside CD");
                    List<Policy> policyList = findAll();
                    for (Policy p : policyList) {
                        if (j < p.getMaxAge() && j > p.getMinAge()) {
                            addPolicy.add(p);
                            searchvalue2 = saveSearch(p);
//
                        }
                    }

                }
                return addPolicy;
            } else if (strings[0].contains("NN")) {
//                System.out.println("Added to tokenList");
                tokenList.add(strings[0].split("_")[0]);
//                System.out.println("Added " + tokenList.get(0));

                if (policyString.contains(tokenList.get(0))) {
                    System.out.println(strings[0].split("_")[0]);
                    List<Policy> policies2 = findByPolicyName(tokenList.get(0));
                    for (Policy p : policies2) {
                        searchvalue2 = saveSearch(p);
                    }
                    return policies2;
                } else if (companyString.contains(tokenList.get(0))) {
                    List<Policy> policies2 = findByInsurerName(tokenList.get(0));
                    for (Policy t : policies2) {
                        searchvalue2 = saveSearch(t);
                    }
                    return policies2;
                }
                else if (diseaseString.contains(tokenList.get(0))) {
//                    System.out.println("DiseaseString "+tokenList.get(0));
                    policies = searchRepository.findAll();

                    for (int i=0;i<policies.size();i++) {
//                        System.out.println("In the loop");
                        List<String> str=policies.get(i).getDiseasesCovered();
                        System.out.println(str);
                        if(str.size() > 0) {
                            if(str.contains(tokenList.get(0)))
                            {
                                policies3.add(policies.get(i));
                            }
                        }else{

                        }
                        //searchvalue2 = saveSearch(pol);
                    }
//                    System.out.println("printing the final result");
                    System.out.println(policies3);
                    return policies3;
                }
            }
        } else if (len == 2) {
//            System.out.println("Entered else if");
            for (int in = 0; in < strings.length; in++) {
                System.out.println(strings[in]);
                if (strings[in].contains("NN") && strings[1].contains("CD")) {
                    newpolicy1.add(strings[in].split("_")[0]);
                    newpolicy1.add(strings[1].split("_")[0]);
                    System.out.println(newpolicy1.toString());
                    List<Policy> addPolicy = new ArrayList<>();
                    for (int t = 0; t < newpolicy1.size(); t++) {
                        for (int b = t + 1; b < newpolicy1.size(); b++) {
                            int num = Integer.parseInt(newpolicy1.get(b));
                            if (policyString.contains(newpolicy1.get(t)) && num < 100) {

                                List<Policy> arrayPolicy = findByPolicyName(newpolicy1.get(t));
                                for (Policy pol : arrayPolicy) {
                                    if (pol.getMinAge() < num && pol.getMaxAge() > num) {
                                        addPolicy.add(pol);
                                        searchvalue2 = saveSearch(pol);
                                    }
                                }
                                return addPolicy;
                            }
                            else if (policyString.contains(newpolicy1.get(t)) && num >= 1000) {
                                List<Policy> arrayPolicy = findByPolicyName(newpolicy1.get(t));
                                for (Policy pol : arrayPolicy) {
                                    if (pol.getMinSumInsured()<num && pol.getMaxSumInsured()>num) {
                                        addPolicy.add(pol);
                                        searchvalue2=saveSearch(pol);
                                    }
                                }
                                return addPolicy;
                            } else if (diseaseString.contains(newpolicy1.get(t)) && num > 1000) {
                                policies=searchRepository.findAll();
                                for(Policy p:policies) {
                                    if (p.getDiseasesCovered().contains(newpolicy1.get(t))) {
                                        if (p.getMinSumInsured() < num && p.getMaxSumInsured() > num) {
                                            addPolicy.add(p);
                                            searchvalue2 = saveSearch(p);
                                        }
                                    }
                                }
                                return addPolicy;
                            }
                            else if (diseaseString.contains(newpolicy1.get(t)) && num < 100) {
                                System.out.println("Disease name:"+newpolicy1.get(t));
                                policies = searchRepository.findAll();
                                for (Policy p : policies) {
                                    if (p.getDiseasesCovered().contains(newpolicy1.get(t))) {
                                        if (p.getMinAge() < num && p.getMaxAge() > num) {
                                            addPolicy.add(p);
                                            searchvalue2 = saveSearch(p);
                                        }
                                    }
                                }
                                return addPolicy;
                            }
                            else if (companyString.contains(newpolicy1.get(t)) && num < 100) {
//                                System.out.println("Company name:"+newpolicy1.get(t));
                                policies = searchRepository.findAll();
                                for (Policy p : policies) {
                                    if (p.getInsurerName().contains(newpolicy1.get(t))) {
                                        if (p.getMinAge() < num && p.getMaxAge() > num) {
                                            addPolicy.add(p);
                                            searchvalue2 = saveSearch(p);
                                        }
                                    }
                                }
                                return addPolicy;
                            }
                            else if (companyString.contains(newpolicy1.get(t)) && num > 1000) {
                                policies=searchRepository.findAll();
                                for(Policy p:policies) {
                                    if (p.getInsurerName().contains(newpolicy1.get(t))) {
                                        if (p.getMinSumInsured() < num && p.getMaxSumInsured() > num) {
                                            addPolicy.add(p);
                                            searchvalue2 = saveSearch(p);
                                        }
                                    }
                                }
                                return addPolicy;
                            }
                        }
                    }
                } else if (strings[in].contains("CD") && strings[1].contains("NN")) {
                    newpolicy1.add(strings[in].split("_")[0]);
                    newpolicy1.add(strings[1].split("_")[0]);
                    System.out.println(newpolicy1.toString());
                    List<Policy> addPolicy = new ArrayList<>();
                    for (int t = 0; t < newpolicy1.size(); t++) {
                        for (int b = t + 1; b < newpolicy1.size(); b++) {
                            int num = Integer.parseInt(newpolicy1.get(t));
                            if (policyString.contains(newpolicy1.get(b)) && num < 100) {

                                List<Policy> arrayPolicy = findByPolicyName(newpolicy1.get(b));
                                for (Policy pol : arrayPolicy) {
                                    if (pol.getMinAge() < num && pol.getMaxAge() > num) {
                                        addPolicy.add(pol);
                                        searchvalue2 = saveSearch(pol);
                                    }
                                }
                                return addPolicy;
                            }
                            if (companyString.contains(newpolicy1.get(b)) && num < 100) {
                                List<Policy> arrayPolicy = findByInsurerName(newpolicy1.get(b));
                                for (Policy pol : arrayPolicy) {
                                    if (pol.getMinAge() < num && pol.getMaxAge() > num) {
                                        addPolicy.add(pol);
                                        searchvalue2 = saveSearch(pol);
                                    }
                                }
                                return addPolicy;
                            }
                            else if (policyString.contains(newpolicy1.get(b)) && num >= 1000) {
                                List<Policy> arrayPolicy = findByPolicyName(newpolicy1.get(b));
                                for (Policy pol : arrayPolicy) {
                                    if (pol.getMinSumInsured() < num && pol.getMaxSumInsured() >num) {
                                        addPolicy.add(pol);
                                        searchvalue2=saveSearch(pol);
                                    }
                                }
                                return addPolicy;
                            }
                            else if (companyString.contains(newpolicy1.get(b)) && num >= 1000) {
                                List<Policy> arrayPolicy = findByInsurerName(newpolicy1.get(b));
                                for (Policy pol : arrayPolicy) {
                                    if (pol.getMinSumInsured() < num && pol.getMaxSumInsured() >num) {
                                        addPolicy.add(pol);
                                        searchvalue2=saveSearch(pol);
                                    }
                                }
                                return addPolicy;
                            } else if (diseaseString.contains(newpolicy1.get(b)) && num > 1000) {
                                policies=searchRepository.findAll();
                                for(Policy p:policies) {
                                    if (p.getDiseasesCovered().contains(newpolicy1.get(b)))
                                        if (p.getMinSumInsured() < num && p.getMaxSumInsured() > num) {
                                            addPolicy.add(p);
                                            searchvalue2 = saveSearch(p);
                                        }
                                }
                                return addPolicy;
                            }
                            else if (diseaseString.contains(newpolicy1.get(b)) && num < 100) {
                                policies = searchRepository.findAll();
                                for (Policy p : policies) {
                                    if (p.getDiseasesCovered().contains(newpolicy1.get(b))) {
                                        if (p.getMinAge() < num && p.getMaxAge() > num) {
                                            addPolicy.add(p);
                                            searchvalue2 = saveSearch(p);
                                        }
                                    }

                                }
                                return addPolicy;

                            }
                        }

                    }
                } else if (strings[in].contains("NN")) {
//                    System.out.println("Added to tokenList");
                    tokenList.add(strings[in].split("_")[0]);
                }
                for (String t : tokenList) {
//                    System.out.println("tokenList " + t);
                }
                for (int i = 0; i < tokenList.size(); i++) {
                    for (int k = i + 1; k < tokenList.size(); k++) {
                        List<Policy> addPolicy = new ArrayList<>();
                        if (policyString.contains(tokenList.get(i)) && diseaseString.contains(tokenList.get(i + 1))) {
//                            System.out.println("Found " + tokenList.get(k));

                            policies = searchRepository.findAll();
                            for (Policy p : policies) {
                                if (p.getDiseasesCovered().contains(tokenList.get(k))) {
                                    if (p.getPolicyName().equals(tokenList.get(i))) {
//                                        System.out.println("Before adding");
                                        addPolicy.add(p);
                                        searchvalue2 = saveSearch(p);
//                                        System.out.println("Added");
                                    }

                                }
                            }
                            return addPolicy;
                        } else if (diseaseString.contains(tokenList.get(i)) && policyString.contains(tokenList.get(k))) {
                            policies = searchRepository.findAll();
                            for (Policy p : policies) {
                                if (p.getDiseasesCovered().contains(tokenList.get(i))) {
                                    if (tokenList.get(k).equals(p.getPolicyName())) {
                                        addPolicy.add(p);
                                        searchvalue2 = saveSearch(p);
                                    }

                                }
                            }
                            return addPolicy;
                        } else if (diseaseString.contains(tokenList.get(i)) && companyString.contains(tokenList.get(k))) {
                            System.out.println(tokenList.get(i));
                            List<Policy> newPolicy = new ArrayList<>();
                            newPolicy = searchRepository.findAll();
                            for (Policy t : newPolicy) {
                                if (t.getDiseasesCovered().contains(tokenList.get(i))) {
                                    System.out.println(tokenList.get(k));
                                    System.out.println(t.getInsurerName());
                                    if (tokenList.get(k).equals(t.getInsurerName())) {
                                        addPolicy.add(t);
                                        searchvalue2 = saveSearch(t);
                                        System.out.println(t.getInsurerName());
                                        System.out.println(t.toString());

                                    }
                                }
                            }
                            return addPolicy;
                        } else if (companyString.contains(tokenList.get(i)) && diseaseString.contains(tokenList.get(k))) {
                            List<Policy> newPolicy = new ArrayList<>();
                            newPolicy = searchRepository.findAll();
                            for (Policy t : newPolicy) {
                                if (tokenList.get(i).equals(t.getInsurerName())) {
                                    System.out.println(tokenList.get(k));
                                    System.out.println(t.getInsurerName());
                                    if (t.getDiseasesCovered().contains(tokenList.get(k))) {
                                        addPolicy.add(t);
                                        searchvalue2 = saveSearch(t);
                                        System.out.println(t.getInsurerName());
                                        System.out.println(t.toString());

                                    }
                                }

                            }
                            return addPolicy;

                        } else if (companyString.contains(tokenList.get(i))) {
                            List<Policy> policies2 = searchRepository.findByInsurerName(tokenList.get(i));
                            for (Policy p : policies2) {
//                                System.out.println("Policy:: " + p.getPolicyName());
                                if (p.getPolicyName().equals(tokenList.get(k))) {
//                                    System.out.println("TokenList :" + tokenList.get(k));
                                    addPolicy.add(p);
                                    searchvalue2 = saveSearch(p);
//                                    System.out.println("Entered into company and policy");

                                }

                            }
                            return addPolicy;
                        } else if (policyString.contains(tokenList.get(i))) {
                            List<Policy> policies2 = searchRepository.findByPolicyName(tokenList.get(i));
                            System.out.println(policies2.size());
                            for (Policy p : policies2) {
//                                System.out.println(tokenList.get(i));
//                                System.out.println("Policy:: " + p.getInsurerName());
                                if (tokenList.get(k).equals(p.getInsurerName())) {
//                                    System.out.println("TokenList :" + tokenList.get(k));
                                    addPolicy.add(p);
                                    searchvalue2 = saveSearch(p);
//                                    System.out.println("Entered into company and policy");

                                }
                                System.out.println("123");
                            }
                            return addPolicy;

                        }


                    }

                }

            }


        }
        else if(len==3) {
            System.out.println("Inside len 3");
            StringBuilder res = new StringBuilder();
            List<Policy> addPolicy = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                if (strings[i].contains("NN")) {
                    System.out.println("Added to tokenList");
                    res.append(strings[i].split("_")[0]);

                }
                System.out.println(res);
            }
            String s1 = res.toString();
            if (policyString.contains(s1)) {
                System.out.println("Inside....");
                addPolicy = findByPolicyName(s1);
            }
            return addPolicy;

        }
        return  null;
    }
}











