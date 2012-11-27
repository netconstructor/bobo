package com.browseengine.bobo.facets.filter;

import java.io.IOException;

import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.Bits;

import com.browseengine.bobo.api.BoboIndexReader;
import com.browseengine.bobo.docidset.RandomAccessDocIdSet;
import com.kamikaze.docidset.impl.NotDocIdSet;

public class RandomAccessNotFilter extends RandomAccessFilter
{
  protected final RandomAccessFilter _innerFilter;
  
  public RandomAccessNotFilter(RandomAccessFilter innerFilter)
  {
    _innerFilter = innerFilter;
  }
  
  public double getFacetSelectivity(BoboIndexReader reader)
  {
    double selectivity = _innerFilter.getFacetSelectivity(reader);
    selectivity = selectivity > 0.999 ? 0.0 : (1-selectivity); 
    return selectivity;
  }
  
  @Override
  public RandomAccessDocIdSet getRandomAccessDocIdSet(BoboIndexReader reader, Bits liveDocs) throws IOException
  {
    final RandomAccessDocIdSet innerDocIdSet = _innerFilter.getRandomAccessDocIdSet(reader, liveDocs);
    final DocIdSet notInnerDocIdSet = new NotDocIdSet(innerDocIdSet, reader.maxDoc());
    return new RandomAccessDocIdSet()
    {
      @Override
      public boolean get(int docId)
      {
        return !innerDocIdSet.get(docId);
      }
      @Override
      public DocIdSetIterator iterator() throws IOException
      {
        return notInnerDocIdSet.iterator();
      }
    };
  }

}
